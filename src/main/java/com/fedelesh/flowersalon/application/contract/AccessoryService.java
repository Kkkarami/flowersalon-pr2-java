package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.Accessory;
import java.util.List;
import java.util.UUID;

public interface AccessoryService {

  List<Accessory> getAll();

  Accessory getById(UUID id);
}
