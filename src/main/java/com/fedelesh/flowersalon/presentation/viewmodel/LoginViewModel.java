package com.fedelesh.flowersalon.presentation.viewmodel;

import com.fedelesh.flowersalon.application.contract.AuthService;

public class LoginViewModel {

    private final AuthService authService;

    public LoginViewModel(AuthService authService) {
        this.authService = authService;
    }

    public boolean login(String email, String password) {
        return authService.authenticate(email, password);
    }
}