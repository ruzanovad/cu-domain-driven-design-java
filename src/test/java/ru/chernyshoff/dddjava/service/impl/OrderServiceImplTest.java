package ru.chernyshoff.dddjava.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.chernyshoff.dddjava.dao.repository.OrderRepository;
import ru.chernyshoff.dddjava.domain.Order;
import ru.chernyshoff.dddjava.service.OrderService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    void create_shouldSaveAndReturnOrder() {
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UUID clientId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        UUID courierId = UUID.randomUUID();
        List<UUID> dishIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        BigDecimal totalAmount = BigDecimal.valueOf(100);

        Order result = orderService.create(clientId, restaurantId, courierId, dishIds, totalAmount);

        verify(orderRepository).save(any(Order.class));
        assertNotNull(result.id());
        assertEquals(clientId, result.clientId());
        assertEquals(restaurantId, result.restaurantId());
        assertEquals(courierId, result.courierId());
        assertEquals(dishIds, result.dishIds());
        assertEquals("CREATED", result.status());
        assertEquals(totalAmount, result.totalAmount());
        assertNotNull(result.createdAt());
    }

    @Test
    void findById_shouldDelegateToRepository() {
        UUID id = UUID.randomUUID();
        Order order = new Order(id, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                List.of(), Instant.now(), "CREATED", BigDecimal.TEN);
        when(orderRepository.findById(id)).thenReturn(order);

        Order result = orderService.findById(id);

        assertEquals(order, result);
        verify(orderRepository).findById(id);
    }

    @Test
    void update_shouldDelegateToRepository() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                List.of(), Instant.now(), "CREATED", BigDecimal.TEN);
        when(orderRepository.update(order)).thenReturn(order);

        Order result = orderService.update(order);

        assertEquals(order, result);
        verify(orderRepository).update(order);
    }

    @Test
    void delete_shouldDelegateToRepository() {
        UUID id = UUID.randomUUID();

        orderService.delete(id);

        verify(orderRepository).deleteById(id);
    }

    @Test
    void changeStatus_shouldUpdateStatusAndPersist() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                List.of(), Instant.now(), "CREATED", BigDecimal.TEN);
        when(orderRepository.update(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.changeStatus(order, "DELIVERED");

        verify(orderRepository).update(any(Order.class));
        assertEquals("DELIVERED", result.status());
        assertEquals(order.id(), result.id());
    }
}
