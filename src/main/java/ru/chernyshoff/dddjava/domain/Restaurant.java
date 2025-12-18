package ru.chernyshoff.dddjava.domain;

import java.util.List;
import java.util.UUID;

/**
 * Ресторан, из которого клиент может заказывать еду.
 */
public record Restaurant(
        UUID id,
        String name,
        String address,
        double rating,
        List<UUID> dishIds
) {
}
