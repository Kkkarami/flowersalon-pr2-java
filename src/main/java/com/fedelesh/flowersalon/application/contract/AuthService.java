package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.User;

public interface AuthService {

    boolean authenticate(String email, String password);

    User getCurrentUser();

    void logout();
}
