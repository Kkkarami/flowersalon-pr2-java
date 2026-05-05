package com.fedelesh.flowersalon.infrastructure.storage.contract;

import com.fedelesh.flowersalon.domain.entity.Flower;
import com.fedelesh.flowersalon.infrastructure.storage.Repository;
import java.util.List;

public interface FlowerRepository extends Repository<Flower> {

    List<Flower> findByColor(String color);
}
