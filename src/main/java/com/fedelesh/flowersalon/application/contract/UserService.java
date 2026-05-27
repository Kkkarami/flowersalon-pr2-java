package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.domain.enums.Role;
import java.util.List;

public interface UserService {

    List<User> getAll();

    void changeRole(User targetUser, Role newRole, User currentUser);
}
