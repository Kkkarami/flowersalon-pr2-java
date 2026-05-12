package com.fedelesh.flowersalon.infrastructure.security;

public interface PasswordHasher {

  String hash(String rawPassword);

  boolean verify(String rawPassword, String hashedPassword);
}
