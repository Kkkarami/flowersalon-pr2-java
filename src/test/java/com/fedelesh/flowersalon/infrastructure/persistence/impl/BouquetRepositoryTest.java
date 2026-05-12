package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fedelesh.flowersalon.domain.entity.Bouquet;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BouquetRepositoryTest {

  private final BouquetRepositoryImpl repository = new BouquetRepositoryImpl();

  @Test
  void shouldSaveAndFindById() {

    UUID id = UUID.randomUUID();
    UUID createdBy = UUID.fromString("9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4");

    Bouquet bouquet = new Bouquet(id, "Spring", "Desc", false, LocalDateTime.now(), createdBy);

    repository.save(bouquet);

    var result = repository.findById(id);

    assertTrue(result.isPresent());
  }
}
