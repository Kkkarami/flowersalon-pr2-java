package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.Bouquet;
import java.util.List;

public interface BouquetService {

    List<Bouquet> getAll();
}