package ru.chernyshoff.dddjava.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.chernyshoff.dddjava.dao.repository.DishRepository;
import ru.chernyshoff.dddjava.dao.repository.RestaurantRepository;
import ru.chernyshoff.dddjava.domain.Dish;
import ru.chernyshoff.dddjava.domain.Restaurant;
import ru.chernyshoff.dddjava.service.RestaurantService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantServiceImplTest {

    private RestaurantRepository restaurantRepository;
    private DishRepository dishRepository;
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        restaurantRepository = mock(RestaurantRepository.class);
        dishRepository = mock(DishRepository.class);
        restaurantService = new RestaurantServiceImpl(restaurantRepository, dishRepository);
    }

    @Test
    void create_shouldSaveAndReturnRestaurant() {
        when(restaurantRepository.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Restaurant result = restaurantService.create("Test", "Address");

        verify(restaurantRepository).save(any(Restaurant.class));
        assertNotNull(result.id());
        assertEquals("Test", result.name());
        assertEquals("Address", result.address());
        assertEquals(0.0, result.rating());
        assertEquals(List.of(), result.dishIds());
    }

    @Test
    void findById_shouldDelegateToRepository() {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(id, "Test", "Address", 4.5, List.of());
        when(restaurantRepository.findById(id)).thenReturn(restaurant);

        Restaurant result = restaurantService.findById(id);

        assertEquals(restaurant, result);
        verify(restaurantRepository).findById(id);
    }

    @Test
    void update_shouldDelegateToRepository() {
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Test", "Address", 4.5, List.of());
        when(restaurantRepository.update(restaurant)).thenReturn(restaurant);

        Restaurant result = restaurantService.update(restaurant);

        assertEquals(restaurant, result);
        verify(restaurantRepository).update(restaurant);
    }

    @Test
    void delete_shouldDelegateToRepository() {
        UUID id = UUID.randomUUID();

        restaurantService.delete(id);

        verify(restaurantRepository).deleteById(id);
    }

    @Test
    void addDish_shouldAppendDishIdAndPersist() {
        UUID existingDishId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Test", "Address", 4.5, List.of(existingDishId));
        UUID newDishId = UUID.randomUUID();
        when(restaurantRepository.update(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Restaurant result = restaurantService.addDish(restaurant, newDishId);

        verify(restaurantRepository).update(any(Restaurant.class));
        assertEquals(List.of(existingDishId, newDishId), result.dishIds());
    }

    @Test
    void getDishes_shouldLoadDishesFromRepository() {
        UUID dishId1 = UUID.randomUUID();
        UUID dishId2 = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Test", "Address", 4.5, List.of(dishId1, dishId2));

        Dish dish1 = new Dish(dishId1, restaurant.id(), "Pizza", BigDecimal.TEN, "Cheese");
        Dish dish2 = new Dish(dishId2, restaurant.id(), "Soup", BigDecimal.ONE, "Veg");

        when(dishRepository.findById(dishId1)).thenReturn(dish1);
        when(dishRepository.findById(dishId2)).thenReturn(dish2);

        List<Dish> result = restaurantService.getDishes(restaurant);

        assertEquals(List.of(dish1, dish2), result);
        verify(dishRepository).findById(dishId1);
        verify(dishRepository).findById(dishId2);
    }
}
