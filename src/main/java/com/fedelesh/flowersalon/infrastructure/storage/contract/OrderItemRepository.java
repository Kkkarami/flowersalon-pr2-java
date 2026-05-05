package com.fedelesh.flowersalon.infrastructure.storage.contract;

import com.fedelesh.flowersalon.domain.entity.OrderItem;
import com.fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends Repository<OrderItem> {

    List<OrderItem> findByOrderId(UUID orderId);
}