package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.Accessory;
import com.fedelesh.flowersalon.domain.entity.User;
import java.util.List;
import java.util.UUID;

public interface AccessoryService {

    List<Accessory> getAll();

    Accessory getById(UUID id);

    void create(Accessory accessory, User currentUser);

    void update(Accessory accessory, User currentUser);

    void delete(UUID id, User currentUser);
}