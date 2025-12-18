package ru.chernyshoff.dddjava.dao.repository;

import ru.chernyshoff.dddjava.domain.Courier;

import java.util.List;
import java.util.UUID;

/**
 * DAO-репозиторий для работы с сущностью Courier.
 */
public interface CourierRepository {

    Courier save(Courier courier);

    Courier update(Courier courier);

    Courier findById(UUID id);

    List<Courier> findAll();

    void deleteById(UUID id);
}
