package com.fedelesh.flowersalon.domain.entity;

import java.util.UUID;

public class BouquetAccessory {

    private UUID bouquetAccessoryId;
    private UUID bouquetId;
    private UUID accessoryId;
    private int quantity;

    public BouquetAccessory() {}

    public BouquetAccessory(UUID bouquetAccessoryId, UUID bouquetId, UUID accessoryId, int quantity) {

        this.bouquetAccessoryId = bouquetAccessoryId;
        this.bouquetId = bouquetId;
        this.accessoryId = accessoryId;
        this.quantity = quantity;
    }

    public UUID getBouquetAccessoryId() {
        return bouquetAccessoryId;
    }

    public void setBouquetAccessoryId(UUID bouquetAccessoryId) {
        this.bouquetAccessoryId = bouquetAccessoryId;
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
