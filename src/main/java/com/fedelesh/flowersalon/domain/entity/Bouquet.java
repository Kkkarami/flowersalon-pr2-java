package com.fedelesh.flowersalon.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Bouquet {

  private UUID bouquetId;
  private String name;
  private String description;
  private boolean isCustom;
  private LocalDateTime createdAt;
  private UUID createdBy;

  public Bouquet() {}

  public Bouquet(
      UUID bouquetId,
      String name,
      String description,
      boolean isCustom,
      LocalDateTime createdAt,
      UUID createdBy) {
    this.bouquetId = bouquetId;
    this.name = name;
    this.description = description;
    this.isCustom = isCustom;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
  }

  public UUID getBouquetId() {
    return bouquetId;
  }

  public void setBouquetId(UUID bouquetId) {
    this.bouquetId = bouquetId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isCustom() {
    return isCustom;
  }

  public void setCustom(boolean custom) {
    isCustom = custom;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public UUID getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(UUID createdBy) {
    this.createdBy = createdBy;
  }
}
