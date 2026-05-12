package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.FlowerService;
import com.fedelesh.flowersalon.domain.entity.Flower;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.FlowerRepository;
import java.util.List;
import java.util.UUID;

public class FlowerServiceImpl implements FlowerService {

  private final FlowerRepository flowerRepository;

  public FlowerServiceImpl(FlowerRepository flowerRepository) {
    this.flowerRepository = flowerRepository;
  }

  @Override
  public List<Flower> getAll() {
    return flowerRepository.findAll();
  }

  @Override
  public Flower getById(UUID id) {

    return flowerRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Flower not found"));
  }
}
