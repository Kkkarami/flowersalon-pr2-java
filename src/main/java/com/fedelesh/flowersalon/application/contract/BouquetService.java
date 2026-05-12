package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.Bouquet;
import java.util.List;
import java.util.UUID;

public interface BouquetService {

    List<Bouquet> getAll();

    Bouquet getById(UUID id);
}
