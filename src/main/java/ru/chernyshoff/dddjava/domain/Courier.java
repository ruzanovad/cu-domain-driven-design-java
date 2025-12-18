package ru.chernyshoff.dddjava.domain;

import java.util.List;
import java.util.UUID;

/**
 * Курьер, доставляющий заказы.
 */
public record Courier(
        UUID id,
        String name,
        String transportType,
        String status,
        List<UUID> orderIds
) {
}
