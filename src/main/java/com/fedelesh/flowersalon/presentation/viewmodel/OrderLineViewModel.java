package com.fedelesh.flowersalon.presentation.viewmodel;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineViewModel {

    private final String itemType;
    private final String name;
    private final int quantity;
    private final BigDecimal price;
    private final BigDecimal total;
    private final String imagePath;

    private UUID flowerId;
    private UUID bouquetId;
    private UUID accessoryId;

    private double workspaceX;
    private double workspaceY;

    public OrderLineViewModel(
          String itemType,
          String name,
          int quantity,
          BigDecimal price,
          String imagePath) {

        this.itemType = itemType;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.total = price.multiply(BigDecimal.valueOf(quantity));
        this.imagePath = imagePath;
    }

    public String getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getImagePath() {
        return imagePath;
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

    public double getWorkspaceX() {
        return workspaceX;
    }

    public void setWorkspaceX(double workspaceX) {
        this.workspaceX = workspaceX;
    }

    public double getWorkspaceY() {
        return workspaceY;
    }

    public void setWorkspaceY(double workspaceY) {
        this.workspaceY = workspaceY;
    }
}