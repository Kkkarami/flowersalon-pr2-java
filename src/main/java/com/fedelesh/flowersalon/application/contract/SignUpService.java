package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.enums.Role;

public interface SignUpService {

    String generateVerificationCode(String email);

    void register(
            String firstName,
            String lastName,
            String email,
            String phone,
            String password,
            Role role,
            String inputCode);
}
