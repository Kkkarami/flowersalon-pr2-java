package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.AccessoryService;
import com.fedelesh.flowersalon.domain.entity.Accessory;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.AccessoryRepository;
import java.util.List;

public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public AccessoryServiceImpl(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    @Override
    public List<Accessory> getAll() {
        return accessoryRepository.findAll();
    }
}