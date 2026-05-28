package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.SignUpService;
import com.fedelesh.flowersalon.application.validation.ValidationHelper;
import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.domain.enums.Role;
import com.fedelesh.flowersalon.infrastructure.email.EmailSender;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.UserRepository;
import com.fedelesh.flowersalon.infrastructure.security.PasswordHasher;
import com.google.inject.Inject;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class SignUpServiceImpl implements SignUpService {

  private static final int EXP_MINUTES = 5;

  private final UserRepository userRepository;
  private final PasswordHasher passwordHasher;
  private final EmailSender emailSender;

  private String code;
  private String emailForCode;
  private LocalDateTime createdAt;

  @Inject
  public SignUpServiceImpl(
      UserRepository userRepository, PasswordHasher passwordHasher, EmailSender emailSender) {
    this.userRepository = userRepository;
    this.passwordHasher = passwordHasher;
    this.emailSender = emailSender;
  }

  @Override
  public String generateVerificationCode(String email) {

    new ValidationHelper()
        .notEmpty("email", email)
        .validEmail("email", email)
        .addErrorIf(
            userRepository.findByEmail(email).isPresent(),
            "email",
            "Користувач з такою поштою вже існує")
        .throwIfHasErrors();

    this.code = String.valueOf(100000 + new Random().nextInt(900000));
    this.emailForCode = email;
    this.createdAt = LocalDateTime.now();

    emailSender.send(email, "Verification code", "Your code: " + code);

    return code;
  }

  @Override
  public void register(
      String firstName,
      String lastName,
      String email,
      String phone,
      String password,
      Role role,
      String inputCode) {

    ValidationHelper helper = new ValidationHelper();

    helper
        .notEmpty("firstName", firstName)
        .minLength("firstName", firstName, 3)
        .notEmpty("lastName", lastName)
        .minLength("lastName", lastName, 3)
        .notEmpty("email", email)
        .validEmail("email", email)
        .notEmpty("phone", phone)
        .validPhone("phone", phone)
        .notEmpty("password", password)
        .minLength("password", password, 6)
        .notEmpty("inputCode", inputCode);

    helper.addErrorIf(code == null || createdAt == null, "code", "Код не був згенерований");

    helper.addErrorIf(
        userRepository.findByEmail(email).isPresent(), "email", "Користувач вже існує");

    if (code != null && createdAt != null) {

      helper.addErrorIf(!email.equals(emailForCode), "email", "Пошта не співпадає");

      helper.addErrorIf(
          LocalDateTime.now().isAfter(createdAt.plusMinutes(EXP_MINUTES)),
          "code",
          "Код прострочений");

      helper.addErrorIf(!code.equals(inputCode), "code", "Невірний код");
    }

    helper.throwIfHasErrors();

    User user =
        new User(
            UUID.randomUUID(),
            firstName,
            lastName,
            email,
            phone,
            role,
            passwordHasher.hash(password),
            LocalDateTime.now());

    userRepository.save(user);

    code = null;
    emailForCode = null;
    createdAt = null;
  }
}
