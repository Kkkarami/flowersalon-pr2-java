package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.FlowerService;
import com.fedelesh.flowersalon.application.validation.ValidationHelper;
import com.fedelesh.flowersalon.domain.entity.Flower;
import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.domain.enums.Role;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.FlowerRepository;
import com.google.inject.Inject;
import java.util.List;
import java.util.UUID;

public class FlowerServiceImpl implements FlowerService {

  private final FlowerRepository flowerRepository;

  @Inject
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
        .orElseThrow(() -> new RuntimeException("Квітку не знайдено"));
  }

  @Override
  public void create(Flower flower, User currentUser) {
    checkFlorist(currentUser);
    validateFlower(flower);

    flower.setFlowerId(UUID.randomUUID());
    flower.setCreatedBy(currentUser.getUserId());

    flowerRepository.save(flower);
  }

  @Override
  public void update(Flower flower, User currentUser) {
    checkFlorist(currentUser);
    validateFlower(flower);

    flowerRepository.update(flower);
  }

  @Override
  public void delete(UUID id, User currentUser) {
    checkFlorist(currentUser);

    flowerRepository.deleteById(id);
  }

  private void checkFlorist(User currentUser) {
    if (currentUser == null) {
      throw new RuntimeException("Користувач не авторизований");
    }

    if (currentUser.getRole() != Role.FLORIST && currentUser.getRole() != Role.ADMIN) {
      throw new RuntimeException("Недостатньо прав. Доступ лише для флориста або адміністратора");
    }
  }

  private void validateFlower(Flower flower) {
    new ValidationHelper()
        .notEmpty("name", flower.getName())
        .notEmpty("color", flower.getColor())
        .addErrorIf(flower.getPrice() == null, "price", "Ціна обов'язкова")
        .addErrorIf(
            flower.getPrice() != null && flower.getPrice().signum() <= 0,
            "price",
            "Ціна має бути більшою за 0")
        .addErrorIf(
            flower.getStockQuantity() < 0, "stockQuantity", "Кількість не може бути від'ємною")
        .throwIfHasErrors();
  }
}
