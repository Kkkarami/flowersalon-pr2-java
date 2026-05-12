package com.fedelesh.flowersalon.infrastructure.persistence.contract;

import com.fedelesh.flowersalon.domain.entity.BouquetAccessory;
import com.fedelesh.flowersalon.infrastructure.persistence.Repository;
import java.util.List;
import java.util.UUID;

public interface BouquetAccessoryRepository extends Repository<BouquetAccessory> {

    List<BouquetAccessory> findByBouquetId(UUID bouquetId);
}
