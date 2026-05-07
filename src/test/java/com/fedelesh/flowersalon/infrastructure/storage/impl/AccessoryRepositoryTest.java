package com.fedelesh.flowersalon.infrastructure.storage.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fedelesh.flowersalon.domain.entity.Accessory;
import com.fedelesh.flowersalon.domain.enums.AccessoryType;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AccessoryRepositoryTest {

    private final AccessoryRepositoryImpl repository = new AccessoryRepositoryImpl();

    @Test
    void shouldSaveAndFindById() {

        Accessory accessory = new Accessory(
              UUID.randomUUID(),
              "Ribbon",
              AccessoryType.DECOR,
              "Red",
              BigDecimal.valueOf(50),
              10
        );

        repository.save(accessory);

        var result = repository.findById(accessory.getAccessoryId());

        assertTrue(result.isPresent());
    }
}