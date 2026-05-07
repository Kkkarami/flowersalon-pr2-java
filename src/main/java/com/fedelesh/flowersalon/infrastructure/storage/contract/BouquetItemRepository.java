package com.fedelesh.flowersalon.infrastructure.storage.contract;

import com.fedelesh.flowersalon.domain.entity.BouquetItem;
import com.fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.List;
import java.util.UUID;

public interface BouquetItemRepository extends Repository<BouquetItem> {

    List<BouquetItem> findByBouquetId(UUID bouquetId);
}