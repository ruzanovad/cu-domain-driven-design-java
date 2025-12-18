package ru.chernyshoff.dddjava.dao.repository;

import ru.chernyshoff.dddjava.domain.Order;

import java.util.List;
import java.util.UUID;

/**
 * DAO-репозиторий для работы с сущностью Order.
 */
public interface OrderRepository {

    Order save(Order order);

    Order update(Order order);

    Order findById(UUID id);

    List<Order> findAll();

    void deleteById(UUID id);
}
