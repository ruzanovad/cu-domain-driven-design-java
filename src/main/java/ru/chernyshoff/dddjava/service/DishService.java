package ru.chernyshoff.dddjava.service;

import ru.chernyshoff.dddjava.domain.Dish;

import java.math.BigDecimal;
import java.util.UUID;


public interface DishService {

    Dish create(UUID restaurantId, String name, BigDecimal price, String description);

    Dish findById(UUID dishId);

    Dish update(Dish dish);

    void delete(UUID dishId);

    Dish changePrice(Dish dish, BigDecimal newPrice);

    Dish changeDescription(Dish dish, String newDescription);
}

