package ru.chernyshoff.dddjava.dao.repository;

import ru.chernyshoff.dddjava.domain.Restaurant;

import java.util.List;
import java.util.UUID;

/**
 * DAO-репозиторий для работы с сущностью Restaurant.
 */
public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

    Restaurant findById(UUID id);

    List<Restaurant> findAll();

    void deleteById(UUID id);
}
