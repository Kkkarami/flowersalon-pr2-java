package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.domain.entity.OrderItem;
import com.fedelesh.flowersalon.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderItemRepositoryTest {

  private final OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();
  private final OrderItemRepositoryImpl repository = new OrderItemRepositoryImpl();

  @Test
  void shouldFindByOrderId() {

    UUID orderId = UUID.randomUUID();
    UUID userId = UUID.fromString("9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4");
    UUID bouquetId = UUID.fromString("e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4");
    UUID accessoryId = UUID.fromString("f21a9c10-4b8d-4a33-9c11-7d8e2f91a6b5");

    Order order =
        new Order(
            orderId,
            userId,
            "Name",
            "Surname",
            "+380000000000",
            BigDecimal.valueOf(500),
            "romantic",
            "red",
            OrderStatus.NEW,
            LocalDateTime.now());

    orderRepository.save(order);

    OrderItem item =
        new OrderItem(
            UUID.randomUUID(),
            orderId,
            "COMBINED",
            null,
            bouquetId,
            accessoryId,
            2,
            BigDecimal.valueOf(100),
            0,
            0);

    repository.save(item);

    List<OrderItem> result = repository.findByOrderId(orderId);

    assertFalse(result.isEmpty());
  }
}
