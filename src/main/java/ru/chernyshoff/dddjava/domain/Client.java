package ru.chernyshoff.dddjava.domain;

import java.util.List;
import java.util.UUID;

/**
 * Клиент сервиса доставки еды.
 */
public record Client(
        UUID id,
        String name,
        String phone,
        String deliveryAddress,
        List<Order> orders
) {
}
