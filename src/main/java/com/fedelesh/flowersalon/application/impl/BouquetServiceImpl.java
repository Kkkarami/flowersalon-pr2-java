package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.BouquetService;
import com.fedelesh.flowersalon.application.validation.ValidationHelper;
import com.fedelesh.flowersalon.domain.entity.Bouquet;
import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.domain.enums.Role;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.BouquetRepository;
import com.google.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BouquetServiceImpl implements BouquetService {

  private final BouquetRepository bouquetRepository;

  @Inject
  public BouquetServiceImpl(BouquetRepository bouquetRepository) {
    this.bouquetRepository = bouquetRepository;
  }

  @Override
  public List<Bouquet> getAll() {
    return bouquetRepository.findAll();
  }

  @Override
  public Bouquet getById(UUID id) {
    return bouquetRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Букет не знайдено"));
  }

  @Override
  public void create(Bouquet bouquet, User currentUser) {
    checkCatalogAccess(currentUser);
    validateBouquet(bouquet);

    bouquet.setBouquetId(UUID.randomUUID());
    bouquet.setCreatedBy(currentUser.getUserId());

    if (bouquet.getCreatedAt() == null) {
      bouquet.setCreatedAt(LocalDateTime.now());
    }

    bouquetRepository.save(bouquet);
  }

  @Override
  public void update(Bouquet bouquet, User currentUser) {
    checkCatalogAccess(currentUser);
    validateBouquet(bouquet);

    bouquetRepository.update(bouquet);
  }

  @Override
  public void delete(UUID id, User currentUser) {
    checkCatalogAccess(currentUser);

    bouquetRepository.deleteById(id);
  }

  private void checkCatalogAccess(User currentUser) {
    if (currentUser == null) {
      throw new RuntimeException("Користувач не авторизований");
    }

    if (currentUser.getRole() != Role.FLORIST && currentUser.getRole() != Role.ADMIN) {
      throw new RuntimeException("Недостатньо прав");
    }
  }

  private void validateBouquet(Bouquet bouquet) {
    new ValidationHelper()
        .notEmpty("name", bouquet.getName())
        .maxLength("name", bouquet.getName(), 100)
        .maxLength("description", bouquet.getDescription(), 1000)
        .addErrorIf(bouquet.getPrice() == null, "price", "Ціна обов'язкова")
        .addErrorIf(
            bouquet.getPrice() != null && bouquet.getPrice().signum() <= 0,
            "price",
            "Ціна має бути більшою за 0")
        .throwIfHasErrors();
  }
}
