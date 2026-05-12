package com.fedelesh.flowersalon.infrastructure.security;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptPasswordHasher implements PasswordHasher {

  @Override
  public String hash(String rawPassword) {
    return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
  }

  @Override
  public boolean verify(String rawPassword, String hashedPassword) {
    return BCrypt.checkpw(rawPassword, hashedPassword);
  }
}
