package com.fedelesh.flowersalon.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Flower {

    private UUID flowerId;
    private String name;
    private String color;
    private BigDecimal price;
    private int stockQuantity;
    private UUID createdBy;

    public Flower() {
    }

    public Flower(UUID flowerId, String name, String color, BigDecimal price,
          int stockQuantity,
          UUID createdBy) {
        this.flowerId = flowerId;
        this.name = name;
        this.color = color;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdBy = createdBy;
    }

    public UUID getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(UUID flowerId) {
        this.flowerId = flowerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }
}

