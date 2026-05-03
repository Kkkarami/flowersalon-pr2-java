package com.fedelesh.flowersalon.domain.entity;

import java.util.UUID;

public class BouquetItem {

    private UUID bouquetId;
    private UUID flowerId;
    private int quantity;

    public BouquetItem() {
    }

    public BouquetItem(UUID bouquetId, UUID flowerId, int quantity) {
        this.bouquetId = bouquetId;
        this.flowerId = flowerId;
        this.quantity = quantity;
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