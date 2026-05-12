package com.fedelesh.flowersalon.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {

    private UUID orderItemId;
    private UUID orderId;
    private String itemType;
    private UUID flowerId;
    private UUID bouquetId;
    private UUID accessoryId;
    private int quantity;
    private BigDecimal priceSnapshot;

    public OrderItem() {}

    public OrderItem(
            UUID orderItemId,
            UUID orderId,
            String itemType,
            UUID flowerId,
            UUID bouquetId,
            UUID accessoryId,
            int quantity,
            BigDecimal priceSnapshot) {

        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.itemType = itemType;
        this.flowerId = flowerId;
        this.bouquetId = bouquetId;
        this.accessoryId = accessoryId;
        this.quantity = quantity;
        this.priceSnapshot = priceSnapshot;
    }

    public UUID getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(UUID orderItemId) {
        this.orderItemId = orderItemId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public UUID getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(UUID flowerId) {
        this.flowerId = flowerId;
    }

    public UUID getBouquetId() {
        return bouquetId;
    }

    public void setBouquetId(UUID bouquetId) {
        this.bouquetId = bouquetId;
    }

    public UUID getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(UUID accessoryId) {
        this.accessoryId = accessoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceSnapshot() {
        return priceSnapshot;
    }

    public void setPriceSnapshot(BigDecimal priceSnapshot) {
        this.priceSnapshot = priceSnapshot;
    }
}
