package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.AccessoryService;
import com.fedelesh.flowersalon.application.validation.ValidationHelper;
import com.fedelesh.flowersalon.domain.entity.Accessory;
import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.domain.enums.Role;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.AccessoryRepository;
import com.google.inject.Inject;
import java.util.List;
import java.util.UUID;

public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository accessoryRepository;

    @Inject
    public AccessoryServiceImpl(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    @Override
    public List<Accessory> getAll() {
        return accessoryRepository.findAll();
    }

    @Override
    public Accessory getById(UUID id) {
        return accessoryRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Аксесуар не знайдено"));
    }

    @Override
    public void create(Accessory accessory, User currentUser) {
        checkCatalogAccess(currentUser);
        validateAccessory(accessory);

        accessory.setAccessoryId(UUID.randomUUID());

        accessoryRepository.save(accessory);
    }

    @Override
    public void update(Accessory accessory, User currentUser) {
        checkCatalogAccess(currentUser);
        validateAccessory(accessory);

        accessoryRepository.update(accessory);
    }

    @Override
    public void delete(UUID id, User currentUser) {
        checkCatalogAccess(currentUser);

        accessoryRepository.deleteById(id);
    }

    private void checkCatalogAccess(User currentUser) {
        if (currentUser == null) {
            throw new RuntimeException("Користувач не авторизований");
        }

        if (currentUser.getRole() != Role.FLORIST && currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Недостатньо прав");
        }
    }

    private void validateAccessory(Accessory accessory) {
        new ValidationHelper()
              .notEmpty("name", accessory.getName())
              .addErrorIf(accessory.getAccessoryType() == null, "accessoryType", "Тип аксесуара обов'язковий")
              .addErrorIf(accessory.getPrice() == null, "price", "Ціна обов'язкова")
              .addErrorIf(accessory.getPrice() != null && accessory.getPrice().signum() <= 0, "price", "Ціна має бути більшою за 0")
              .addErrorIf(accessory.getStockQuantity() < 0, "stockQuantity", "Кількість не може бути від'ємною")
              .throwIfHasErrors();
    }
}