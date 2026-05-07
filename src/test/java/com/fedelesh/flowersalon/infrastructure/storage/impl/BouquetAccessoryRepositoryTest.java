package com.fedelesh.flowersalon.infrastructure.storage.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fedelesh.flowersalon.domain.entity.BouquetAccessory;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BouquetAccessoryRepositoryTest {

    private final BouquetAccessoryRepositoryImpl repository = new BouquetAccessoryRepositoryImpl();

    @Test
    void shouldFindByBouquetId() {

        UUID bouquetId = UUID.fromString("e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4");
        UUID accessoryId = UUID.fromString("f21a9c10-4b8d-4a33-9c11-7d8e2f91a6b5");

        BouquetAccessory accessory = new BouquetAccessory(
              UUID.randomUUID(),
              bouquetId,
              accessoryId,
              2
        );

        repository.save(accessory);

        List<BouquetAccessory> result = repository.findByBouquetId(bouquetId);

        assertFalse(result.isEmpty());
    }
}