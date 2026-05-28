package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderRepositoryTest {

  private final OrderRepositoryImpl repository = new OrderRepositoryImpl();

  @Test
  void shouldFindByUserId() {

    UUID userId = UUID.fromString("9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4");

    Order order =
        new Order(
            UUID.randomUUID(),
            userId,
            "Name",
            "Surname",
            "+380000000000",
            BigDecimal.valueOf(500),
            "romantic",
            "red",
            OrderStatus.NEW,
            LocalDateTime.now());

    repository.save(order);

    List<Order> result = repository.findByUserId(userId);

    assertFalse(result.isEmpty());
  }
}
