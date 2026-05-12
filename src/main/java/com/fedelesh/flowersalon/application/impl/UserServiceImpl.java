package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.UserService;
import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.UserRepository;
import java.util.List;

public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {

    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAll() {

    return userRepository.findAll();
  }
}
