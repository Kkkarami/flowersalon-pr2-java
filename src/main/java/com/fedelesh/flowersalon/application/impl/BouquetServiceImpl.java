package com.fedelesh.flowersalon.application.impl;

import com.fedelesh.flowersalon.application.contract.BouquetService;
import com.fedelesh.flowersalon.domain.entity.Bouquet;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.BouquetRepository;
import java.util.List;
import java.util.UUID;

public class BouquetServiceImpl implements BouquetService {

    private final BouquetRepository bouquetRepository;

    public BouquetServiceImpl(BouquetRepository bouquetRepository) {
        this.bouquetRepository = bouquetRepository;
    }

    @Override
    public List<Bouquet> getAll() {
        return bouquetRepository.findAll();
    }

    @Override
    public Bouquet getById(UUID id) {

        return bouquetRepository.findById(id).orElseThrow(() -> new RuntimeException("Bouquet not found"));
    }
}
