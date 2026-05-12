package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.domain.entity.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderService {

  void createOrder(Order order, List<OrderItem> items);

  Order getById(UUID id);

  List<Order> getAll();

  List<Order> getByUserId(UUID userId);

  void delete(UUID id);

  BigDecimal calculateTotal(List<OrderItem> items);

  List<OrderItem> getItemsByOrderId(UUID orderId);

  void update(Order order);
}
