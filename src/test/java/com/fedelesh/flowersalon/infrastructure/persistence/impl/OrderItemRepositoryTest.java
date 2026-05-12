package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fedelesh.flowersalon.domain.entity.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderItemRepositoryTest {

  private final OrderItemRepositoryImpl repository = new OrderItemRepositoryImpl();

  @Test
  void shouldFindByOrderId() {

    UUID orderId = UUID.fromString("7c91a2f4-3d8e-4b10-9a77-1f2c9e33b6d8");
    UUID bouquetId = UUID.fromString("e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4");
    UUID accessoryId = UUID.fromString("d91b2c44-6f10-4e88-8a2c-1b7d9f33a5e2");

    OrderItem item =
        new OrderItem(
            UUID.randomUUID(),
            orderId,
            "COMBINED",
            null,
            bouquetId,
            accessoryId,
            2,
            BigDecimal.valueOf(100));

    repository.save(item);

    List<OrderItem> result = repository.findByOrderId(orderId);

    assertFalse(result.isEmpty());
  }
}
