package com.fedelesh.flowersalon.infrastructure.storage.contract;

import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends Repository<Order> {

    List<Order> findByUserId(UUID userId);
}