package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.OrderService;
import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.domain.entity.OrderItem;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.OrderItemRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;

  public OrderServiceImpl(
      OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
    this.orderRepository = orderRepository;
    this.orderItemRepository = orderItemRepository;
  }

  @Override
  public void createOrder(Order order, List<OrderItem> items) {

    UUID orderId = UUID.randomUUID();
    order.setOrderId(orderId);

    if (order.getCreatedAt() == null) {
      order.setCreatedAt(LocalDateTime.now());
    }

    orderRepository.save(order);

    for (OrderItem item : items) {

      item.setOrderItemId(UUID.randomUUID());
      item.setOrderId(orderId);

      orderItemRepository.save(item);
    }
  }

  @Override
  public Order getById(UUID id) {
    return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
  }

  @Override
  public List<Order> getAll() {
    return orderRepository.findAll();
  }

  @Override
  public List<Order> getByUserId(UUID userId) {
    return orderRepository.findByUserId(userId);
  }

  @Override
  public void delete(UUID id) {
    List<OrderItem> items = orderItemRepository.findByOrderId(id);

    for (OrderItem item : items) {
      orderItemRepository.deleteById(item.getOrderItemId());
    }

    orderRepository.deleteById(id);
  }

  @Override
  public BigDecimal calculateTotal(List<OrderItem> items) {
    BigDecimal total = BigDecimal.ZERO;

    for (OrderItem item : items) {
      BigDecimal line = item.getPriceSnapshot().multiply(BigDecimal.valueOf(item.getQuantity()));
      total = total.add(line);
    }

    return total;
  }

  @Override
  public void update(Order order) {

    orderRepository.update(order);
  }

  @Override
  public List<OrderItem> getItemsByOrderId(UUID orderId) {

    return orderItemRepository.findByOrderId(orderId);
  }
}
