package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.Flower;
import java.util.List;

public interface FlowerService {

    List<Flower> getAll();
}