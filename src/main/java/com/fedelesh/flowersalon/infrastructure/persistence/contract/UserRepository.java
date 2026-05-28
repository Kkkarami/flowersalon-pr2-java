package com.fedelesh.flowersalon.infrastructure.persistence.contract;

import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.infrastructure.persistence.Repository;
import java.util.Optional;

public interface UserRepository extends Repository<User> {

  Optional<User> findByEmail(String email);
}
