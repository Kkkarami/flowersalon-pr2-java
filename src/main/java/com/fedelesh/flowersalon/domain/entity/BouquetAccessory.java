package com.fedelesh.flowersalon.domain.entity;

import java.util.UUID;

public class BouquetAccessory {

    private UUID bouquetId;
    private UUID accessoryId;
    private int quantity;

    public BouquetAccessory() {
    }

    public BouquetAccessory(UUID bouquetId, UUID accessoryId, int quantity) {
        this.bouquetId = bouquetId;
        this.accessoryId = accessoryId;
        this.quantity = quantity;
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
}