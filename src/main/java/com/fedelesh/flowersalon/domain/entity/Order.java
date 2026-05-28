package com.fedelesh.flowersalon.domain.entity;

import com.fedelesh.flowersalon.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Order {

  private UUID orderId;
  private UUID userId;
  private String customerFirstName;
  private String customerLastName;
  private String phone;
  private BigDecimal budget;
  private String style;
  private String preferredColor;
  private OrderStatus status;
  private LocalDateTime createdAt;

  public Order() {}

  public Order(
      UUID orderId,
      UUID userId,
      String customerFirstName,
      String customerLastName,
      String phone,
      BigDecimal budget,
      String style,
      String preferredColor,
      OrderStatus status,
      LocalDateTime createdAt) {

    this.orderId = orderId;
    this.userId = userId;
    this.customerFirstName = customerFirstName;
    this.customerLastName = customerLastName;
    this.phone = phone;
    this.budget = budget;
    this.style = style;
    this.preferredColor = preferredColor;
    this.status = status;
    this.createdAt = createdAt;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public void setOrderId(UUID orderId) {
    this.orderId = orderId;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public String getCustomerFirstName() {
    return customerFirstName;
  }

  public void setCustomerFirstName(String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }

  public String getCustomerLastName() {
    return customerLastName;
  }

  public void setCustomerLastName(String customerLastName) {
    this.customerLastName = customerLastName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public String getPreferredColor() {
    return preferredColor;
  }

  public void setPreferredColor(String preferredColor) {
    this.preferredColor = preferredColor;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
