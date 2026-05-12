package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fedelesh.flowersalon.domain.entity.BouquetItem;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BouquetItemRepositoryTest {

    private final BouquetItemRepositoryImpl repository = new BouquetItemRepositoryImpl();

    @Test
    void shouldFindByBouquetId() {

        UUID bouquetId = UUID.fromString("e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4");
        UUID flowerId = UUID.fromString("a11c9e33-7b2d-4f10-9d88-3c5f91a2e7b6");

        BouquetItem item = new BouquetItem(UUID.randomUUID(), bouquetId, flowerId, 3);

        repository.save(item);

        List<BouquetItem> result = repository.findByBouquetId(bouquetId);

        assertFalse(result.isEmpty());
    }
}
