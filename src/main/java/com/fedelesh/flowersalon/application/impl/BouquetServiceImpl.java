package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.BouquetService;
import com.fedelesh.flowersalon.domain.entity.Bouquet;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.BouquetRepository;
import java.util.List;

public class BouquetServiceImpl implements BouquetService {

    private final BouquetRepository bouquetRepository;

    public BouquetServiceImpl(BouquetRepository bouquetRepository) {
        this.bouquetRepository = bouquetRepository;
    }

    @Override
    public List<Bouquet> getAll() {
        return bouquetRepository.findAll();
    }
}