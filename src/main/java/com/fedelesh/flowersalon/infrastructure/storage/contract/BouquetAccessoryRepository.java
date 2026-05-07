package com.fedelesh.flowersalon.infrastructure.storage.contract;

import com.fedelesh.flowersalon.domain.entity.BouquetAccessory;
import com.fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.List;
import java.util.UUID;

public interface BouquetAccessoryRepository extends Repository<BouquetAccessory> {

    List<BouquetAccessory> findByBouquetId(UUID bouquetId);
}