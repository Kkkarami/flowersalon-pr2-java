package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.UserService;
import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.domain.enums.Role;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.UserRepository;
import com.google.inject.Inject;
import java.util.List;

public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Inject
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public void changeRole(User targetUser, Role newRole, User currentUser) {
    if (currentUser == null) {
      return;
    }

    if (targetUser == null) {
      return;
    }

    if (newRole == null) {
      return;
    }

    if (currentUser.getRole() != Role.ADMIN) {
      return;
    }

    if (targetUser.getRole() == Role.ADMIN) {
      return;
    }

    if (newRole == Role.ADMIN) {
      return;
    }

    targetUser.setRole(newRole);
    userRepository.update(targetUser);
  }
}
