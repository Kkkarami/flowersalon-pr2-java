package com.fedelesh.flowersalon.presentation.viewmodel;

import com.fedelesh.flowersalon.application.contract.AuthService;

public record LoginViewModel(AuthService authService) {

    public boolean login(String email, String password) {
        return authService.authenticate(email, password);
    }
}
