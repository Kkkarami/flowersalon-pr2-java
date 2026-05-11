package com.fedelesh.flowersalon.bootstrap;

import com.fedelesh.flowersalon.application.contract.AuthService;
import com.fedelesh.flowersalon.application.contract.SignUpService;
import com.fedelesh.flowersalon.application.impl.AuthServiceImpl;
import com.fedelesh.flowersalon.application.impl.SignUpServiceImpl;
import com.fedelesh.flowersalon.infrastructure.email.EmailSender;
import com.fedelesh.flowersalon.infrastructure.email.SmtpEmailSender;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.UserRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.UserRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.security.BcryptPasswordHasher;
import com.fedelesh.flowersalon.infrastructure.security.PasswordHasher;

public class AppModule {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final EmailSender emailSender;

    private final AuthService authService;
    private final SignUpService signUpService;

    public AppModule() {

        this.userRepository = new UserRepositoryImpl();
        this.passwordHasher = new BcryptPasswordHasher();
        this.emailSender = new SmtpEmailSender();

        this.authService = new AuthServiceImpl(userRepository, passwordHasher);
        this.signUpService = new SignUpServiceImpl(userRepository, passwordHasher, emailSender);
    }

    public AuthService authService() {
        return authService;
    }

    public SignUpService signUpService() {
        return signUpService;
    }
}