package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.User;
import java.util.List;

public interface UserService {

    List<User> getAll();
}
