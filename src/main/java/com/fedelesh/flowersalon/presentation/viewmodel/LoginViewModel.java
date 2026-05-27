package com.fedelesh.flowersalon.presentation.viewmodel;

import com.fedelesh.flowersalon.application.contract.AuthService;
import com.google.inject.Inject;

public class LoginViewModel {

    private final AuthService authService;

    @Inject
    public LoginViewModel(AuthService authService) {
        this.authService = authService;
    }

    public boolean login(String email, String password) {
        return authService.authenticate(email, password);
    }
}