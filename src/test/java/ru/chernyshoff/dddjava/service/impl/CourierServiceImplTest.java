package ru.chernyshoff.dddjava.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.chernyshoff.dddjava.dao.repository.CourierRepository;
import ru.chernyshoff.dddjava.dao.repository.OrderRepository;
import ru.chernyshoff.dddjava.domain.Courier;
import ru.chernyshoff.dddjava.domain.Order;
import ru.chernyshoff.dddjava.service.CourierService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourierServiceImplTest {

    private CourierRepository courierRepository;
    private OrderRepository orderRepository;
    private CourierService courierService;

    @BeforeEach
    void setUp() {
        courierRepository = mock(CourierRepository.class);
        orderRepository = mock(OrderRepository.class);
        courierService = new CourierServiceImpl(courierRepository, orderRepository);
    }

    @Test
    void create_shouldSaveAndReturnCourier() {
        when(courierRepository.save(any(Courier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Courier result = courierService.create("Alex", "bike");

        verify(courierRepository).save(any(Courier.class));
        assertNotNull(result.id());
        assertEquals("Alex", result.name());
        assertEquals("bike", result.transportType());
        assertEquals("AVAILABLE", result.status());
        assertEquals(List.of(), result.orderIds());
    }

    @Test
    void findById_shouldDelegateToRepository() {
        UUID id = UUID.randomUUID();
        Courier courier = new Courier(id, "Alex", "bike", "AVAILABLE", List.of());
        when(courierRepository.findById(id)).thenReturn(courier);

        Courier result = courierService.findById(id);

        assertEquals(courier, result);
        verify(courierRepository).findById(id);
    }

    @Test
    void update_shouldDelegateToRepository() {
        Courier courier = new Courier(UUID.randomUUID(), "Alex", "bike", "AVAILABLE", List.of());
        when(courierRepository.update(courier)).thenReturn(courier);

        Courier result = courierService.update(courier);

        assertEquals(courier, result);
        verify(courierRepository).update(courier);
    }

    @Test
    void delete_shouldDelegateToRepository() {
        UUID id = UUID.randomUUID();

        courierService.delete(id);

        verify(courierRepository).deleteById(id);
    }

    @Test
    void changeStatus_shouldUpdateStatusAndPersist() {
        Courier courier = new Courier(UUID.randomUUID(), "Alex", "bike", "AVAILABLE", List.of());
        when(courierRepository.update(any(Courier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Courier result = courierService.changeStatus(courier, "BUSY");

        verify(courierRepository).update(any(Courier.class));
        assertEquals("BUSY", result.status());
        assertEquals(courier.id(), result.id());
    }

    @Test
    void assignOrder_shouldAppendOrderIdAndPersist() {
        UUID existingOrderId = UUID.randomUUID();
        Courier courier = new Courier(UUID.randomUUID(), "Alex", "bike", "AVAILABLE", List.of(existingOrderId));
        UUID newOrderId = UUID.randomUUID();
        when(courierRepository.update(any(Courier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Courier result = courierService.assignOrder(courier, newOrderId);

        verify(courierRepository).update(any(Courier.class));
        assertEquals(List.of(existingOrderId, newOrderId), result.orderIds());
    }

    @Test
    void getOrders_shouldLoadOrdersFromRepository() {
        UUID orderId1 = UUID.randomUUID();
        UUID orderId2 = UUID.randomUUID();
        Courier courier = new Courier(UUID.randomUUID(), "Alex", "bike", "AVAILABLE", List.of(orderId1, orderId2));

        Order order1 = new Order(orderId1, UUID.randomUUID(), UUID.randomUUID(), courier.id(),
                List.of(), Instant.now(), "CREATED", BigDecimal.TEN);
        Order order2 = new Order(orderId2, UUID.randomUUID(), UUID.randomUUID(), courier.id(),
                List.of(), Instant.now(), "CREATED", BigDecimal.ONE);

        when(orderRepository.findById(orderId1)).thenReturn(order1);
        when(orderRepository.findById(orderId2)).thenReturn(order2);

        List<Order> result = courierService.getOrders(courier);

        assertEquals(List.of(order1, order2), result);
        verify(orderRepository).findById(orderId1);
        verify(orderRepository).findById(orderId2);
    }
}
