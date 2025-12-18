package ru.chernyshoff.dddjava.service;

import ru.chernyshoff.dddjava.domain.Dish;
import ru.chernyshoff.dddjava.domain.Restaurant;

import java.util.List;
import java.util.UUID;

public interface RestaurantService {

    Restaurant create(String name, String address);

    Restaurant findById(UUID restaurantId);

    Restaurant update(Restaurant restaurant);

    void delete(UUID restaurantId);

//    Restaurant changeRating(Restaurant restaurant, double newRating);

    Restaurant addDish(Restaurant restaurant, UUID dishId);

    List<Dish> getDishes(Restaurant restaurant);
}

