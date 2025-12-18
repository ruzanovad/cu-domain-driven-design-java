package ru.chernyshoff.dddjava.dao.repository;

import ru.chernyshoff.dddjava.domain.Dish;

import java.util.List;
import java.util.UUID;

/**
 * DAO-репозиторий для работы с сущностью Dish.
 */
public interface DishRepository {

    Dish save(Dish dish);

    Dish update(Dish dish);

    Dish findById(UUID id);

    List<Dish> findAll();

    void deleteById(UUID id);
}
