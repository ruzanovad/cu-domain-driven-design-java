package ru.chernyshoff.dddjava.service;

import ru.chernyshoff.dddjava.domain.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public interface OrderService {

    Order create(UUID clientId,
                 UUID restaurantId,
                 UUID courierId,
                 List<UUID> dishIds,
                 BigDecimal totalAmount);

    Order findById(UUID orderId);

    Order update(Order order);

    void delete(UUID orderId);

    Order changeStatus(Order order, String newStatus);
}

