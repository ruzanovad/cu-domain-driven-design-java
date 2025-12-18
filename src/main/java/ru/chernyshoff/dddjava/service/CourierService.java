package ru.chernyshoff.dddjava.service;

import ru.chernyshoff.dddjava.domain.Courier;
import ru.chernyshoff.dddjava.domain.Order;

import java.util.List;
import java.util.UUID;


public interface CourierService {

    Courier create(String name, String transportType);

    Courier findById(UUID courierId);

    Courier update(Courier courier);

    void delete(UUID courierId);

    Courier changeStatus(Courier courier, String newStatus);

    Courier assignOrder(Courier courier, UUID orderId);

    List<Order> getOrders(Courier courier);
}

