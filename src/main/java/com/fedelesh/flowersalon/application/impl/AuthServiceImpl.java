package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.AuthService;
import com.fedelesh.flowersalon.application.exception.AuthenticationException;
import com.fedelesh.flowersalon.application.validation.ValidationHelper;
import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.UserRepository;
import com.fedelesh.flowersalon.infrastructure.security.PasswordHasher;
import com.google.inject.Inject;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    private User currentUser;

    @Inject
    public AuthServiceImpl(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public boolean authenticate(String email, String password) {

        new ValidationHelper()
                .notEmpty("email", email)
                .validEmail("email", email)
                .notEmpty("password", password)
                .throwIfHasErrors();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Неправильний логін або пароль"));

        boolean valid = passwordHasher.verify(password, user.getPasswordHash());

        if (!valid) {
            throw new AuthenticationException("Неправильний логін або пароль");
        }

        currentUser = user;
        return true;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void logout() {
        currentUser = null;
    }
}
