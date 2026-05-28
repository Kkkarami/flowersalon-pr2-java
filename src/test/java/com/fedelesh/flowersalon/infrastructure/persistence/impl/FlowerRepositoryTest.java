package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fedelesh.flowersalon.domain.entity.Flower;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class FlowerRepositoryTest {

  private final FlowerRepositoryImpl repository = new FlowerRepositoryImpl();

  @Test
  void shouldSaveAndFindByColor() {

    String color = "Білий";

    UUID createdBy = UUID.fromString("9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4");

    Flower flower =
        new Flower(
            UUID.randomUUID(),
            "Rose",
            color,
            BigDecimal.valueOf(100),
            10,
            createdBy,
            "/images/flowers/test.png");

    repository.save(flower);

    List<Flower> result = repository.findByColor(color);

    assertFalse(result.isEmpty());
  }
}
