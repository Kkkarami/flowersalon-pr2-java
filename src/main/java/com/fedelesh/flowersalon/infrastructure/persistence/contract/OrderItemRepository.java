package com.fedelesh.flowersalon.infrastructure.persistence.contract;

import com.fedelesh.flowersalon.domain.entity.OrderItem;
import com.fedelesh.flowersalon.infrastructure.persistence.Repository;
import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends Repository<OrderItem> {

    List<OrderItem> findByOrderId(UUID orderId);
}
