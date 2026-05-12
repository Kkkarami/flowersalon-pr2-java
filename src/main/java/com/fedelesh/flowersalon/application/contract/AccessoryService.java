package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.Accessory;
import java.util.List;

public interface AccessoryService {

    List<Accessory> getAll();
}