package com.fedelesh.flowersalon.application.contract;

import com.fedelesh.flowersalon.domain.entity.Bouquet;
import com.fedelesh.flowersalon.domain.entity.User;
import java.util.List;
import java.util.UUID;

public interface BouquetService {

    List<Bouquet> getAll();

    Bouquet getById(UUID id);

    void create(Bouquet bouquet, User currentUser);

    void update(Bouquet bouquet, User currentUser);

    void delete(UUID id, User currentUser);
}