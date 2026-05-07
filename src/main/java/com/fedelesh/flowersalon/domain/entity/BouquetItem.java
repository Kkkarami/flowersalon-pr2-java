package com.fedelesh.flowersalon.domain.entity;

import java.util.UUID;

public class BouquetItem {

    private UUID bouquetItemId;
    private UUID bouquetId;
    private UUID flowerId;
    private int quantity;

    public BouquetItem() {
    }

    public BouquetItem(UUID bouquetItemId, UUID bouquetId, UUID flowerId, int quantity) {
        this.bouquetItemId = bouquetItemId;
        this.bouquetId = bouquetId;
        this.flowerId = flowerId;
        this.quantity = quantity;
    }

    public UUID getBouquetItemId() {
        return bouquetItemId;
    }

    public void setBouquetItemId(UUID bouquetItemId) {
        this.bouquetItemId = bouquetItemId;
    }

    public UUID getBouquetId() {
        return bouquetId;
    }

    public void setBouquetId(UUID bouquetId) {
        this.bouquetId = bouquetId;
    }

    public UUID getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(UUID flowerId) {
        this.flowerId = flowerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}