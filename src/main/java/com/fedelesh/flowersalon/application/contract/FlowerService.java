package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.Flower;
import com.fedelesh.flowersalon.domain.entity.User;
import java.util.List;
import java.util.UUID;

public interface FlowerService {

  List<Flower> getAll();

  Flower getById(UUID id);

  void create(Flower flower, User currentUser);

  void update(Flower flower, User currentUser);

  void delete(UUID id, User currentUser);
}
