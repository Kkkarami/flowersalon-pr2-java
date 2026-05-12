package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.Flower;
import java.util.List;
import java.util.UUID;

public interface FlowerService {

  List<Flower> getAll();

  Flower getById(UUID id);
}
