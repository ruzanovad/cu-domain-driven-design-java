package ru.chernyshoff.dddjava.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.chernyshoff.dddjava.dao.repository.DishRepository;
import ru.chernyshoff.dddjava.domain.Dish;
import ru.chernyshoff.dddjava.service.DishService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DishServiceImplTest {

    private DishRepository dishRepository;
    private DishService dishService;

    @BeforeEach
    void setUp() {
        dishRepository = mock(DishRepository.class);
        dishService = new DishServiceImpl(dishRepository);
    }

    @Test
    void create_shouldSaveAndReturnDish() {
        when(dishRepository.save(any(Dish.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UUID restaurantId = UUID.randomUUID();
        Dish result = dishService.create(restaurantId, "Pizza", BigDecimal.TEN, "Cheese");

        verify(dishRepository).save(any(Dish.class));
        assertNotNull(result.id());
        assertEquals(restaurantId, result.restaurantId());
        assertEquals("Pizza", result.name());
        assertEquals(BigDecimal.TEN, result.price());
        assertEquals("Cheese", result.description());
    }

    @Test
    void findById_shouldDelegateToRepository() {
        UUID id = UUID.randomUUID();
        Dish dish = new Dish(id, UUID.randomUUID(), "Pizza", BigDecimal.TEN, "Cheese");
        when(dishRepository.findById(id)).thenReturn(dish);

        Dish result = dishService.findById(id);

        assertEquals(dish, result);
        verify(dishRepository).findById(id);
    }

    @Test
    void update_shouldDelegateToRepository() {
        Dish dish = new Dish(UUID.randomUUID(), UUID.randomUUID(), "Pizza", BigDecimal.TEN, "Cheese");
        when(dishRepository.update(dish)).thenReturn(dish);

        Dish result = dishService.update(dish);

        assertEquals(dish, result);
        verify(dishRepository).update(dish);
    }

    @Test
    void delete_shouldDelegateToRepository() {
        UUID id = UUID.randomUUID();

        dishService.delete(id);

        verify(dishRepository).deleteById(id);
    }

    @Test
    void changePrice_shouldUpdatePriceAndPersist() {
        Dish dish = new Dish(UUID.randomUUID(), UUID.randomUUID(), "Pizza", BigDecimal.TEN, "Cheese");
        when(dishRepository.update(any(Dish.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BigDecimal newPrice = BigDecimal.valueOf(20);
        Dish result = dishService.changePrice(dish, newPrice);

        verify(dishRepository).update(any(Dish.class));
        assertEquals(newPrice, result.price());
        assertEquals(dish.id(), result.id());
    }

    @Test
    void changeDescription_shouldUpdateDescriptionAndPersist() {
        Dish dish = new Dish(UUID.randomUUID(), UUID.randomUUID(), "Pizza", BigDecimal.TEN, "Cheese");
        when(dishRepository.update(any(Dish.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Dish result = dishService.changeDescription(dish, "New desc");

        verify(dishRepository).update(any(Dish.class));
        assertEquals("New desc", result.description());
        assertEquals(dish.id(), result.id());
    }
}
