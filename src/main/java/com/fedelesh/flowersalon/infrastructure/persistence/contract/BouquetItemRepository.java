package com.fedelesh.flowersalon.infrastructure.persistence.contract;

import com.fedelesh.flowersalon.domain.entity.BouquetItem;
import com.fedelesh.flowersalon.infrastructure.persistence.Repository;
import java.util.List;
import java.util.UUID;

public interface BouquetItemRepository extends Repository<BouquetItem> {

    List<BouquetItem> findByBouquetId(UUID bouquetId);
}
