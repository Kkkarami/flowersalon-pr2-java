package com.fedelesh.flowersalon.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Bouquet {

  private UUID bouquetId;
  private String name;
  private String description;
  private BigDecimal price;
  private boolean custom;
  private LocalDateTime createdAt;
  private UUID createdBy;
  private String imagePath;

  public Bouquet() {}

  public Bouquet(
      UUID bouquetId,
      String name,
      String description,
      BigDecimal price,
      boolean custom,
      LocalDateTime createdAt,
      UUID createdBy,
      String imagePath) {

    this.bouquetId = bouquetId;
    this.name = name;
    this.description = description;
    this.price = price;
    this.custom = custom;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.imagePath = imagePath;
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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public boolean isCustom() {
    return custom;
  }

  public void setCustom(boolean custom) {
    this.custom = custom;
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

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }
}
