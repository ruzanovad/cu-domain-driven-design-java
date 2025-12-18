package ru.chernyshoff.dddjava.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Оформленный клиентом заказ.
 */
public record Order(
        UUID id,
        UUID clientId,
        UUID restaurantId,
        UUID courierId,
        List<UUID> dishIds,
        Instant createdAt,
        String status,
        BigDecimal totalAmount
) {
}
