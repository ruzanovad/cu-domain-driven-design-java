package ru.chernyshoff.dddjava.domain;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Блюдо (позиция меню ресторана).
 */
public record Dish(
        UUID id,
        UUID restaurantId,
        String name,
        BigDecimal price,
        String description
) {
}
