package com.fedelesh.flowersalon.domain.entity;

import com.fedelesh.flowersalon.domain.enums.AccessoryType;
import java.math.BigDecimal;
import java.util.UUID;

public class Accessory {

    private UUID accessoryId;
    private String name;
    private AccessoryType accessoryType;
    private String color;
    private BigDecimal price;
    private int stockQuantity;

    public Accessory() {}

    public Accessory(
            UUID accessoryId,
            String name,
            AccessoryType accessoryType,
            String color,
            BigDecimal price,
            int stockQuantity) {
        this.accessoryId = accessoryId;
        this.name = name;
        this.accessoryType = accessoryType;
        this.color = color;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public UUID getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(UUID accessoryId) {
        this.accessoryId = accessoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccessoryType getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(AccessoryType accessoryType) {
        this.accessoryType = accessoryType;
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
}
