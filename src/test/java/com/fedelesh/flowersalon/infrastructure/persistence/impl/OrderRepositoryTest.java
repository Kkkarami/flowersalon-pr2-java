package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fedelesh.flowersalon.domain.entity.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderRepositoryTest {

    private final OrderRepositoryImpl repository = new OrderRepositoryImpl();

    @Test
    void shouldFindByUserId() {

        UUID userId = UUID.fromString("c8a91f44-2d6b-4f91-8a3c-1f9e7b2d5a11");

        Order order = new Order(
              UUID.randomUUID(),
              userId,
              "Name",
              "Surname",
              "+380000000000",
              BigDecimal.valueOf(500),
              "romantic",
              "red",
              LocalDateTime.now()
        );

        repository.save(order);

        List<Order> result = repository.findByUserId(userId);

        assertFalse(result.isEmpty());
    }
}