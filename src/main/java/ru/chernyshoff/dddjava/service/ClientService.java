package ru.chernyshoff.dddjava.service;

import ru.chernyshoff.dddjava.domain.Client;
import ru.chernyshoff.dddjava.domain.Order;

import java.util.List;
import java.util.UUID;


public interface ClientService {

    Client create(String name, String phone, String deliveryAddress);

    Client findById(UUID clientId);

    Client update(Client client);

    void delete(UUID clientId);

    Client changeDeliveryAddress(Client client, String newDeliveryAddress);

    Client addOrder(Client client, UUID orderId);

    List<Order> getOrders(Client client);
}

