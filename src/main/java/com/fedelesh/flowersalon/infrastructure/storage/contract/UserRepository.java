package com.fedelesh.flowersalon.infrastructure.storage.contract;

import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.Optional;

public interface UserRepository extends Repository<User> {

    Optional<User> findByEmail(String email);
}
