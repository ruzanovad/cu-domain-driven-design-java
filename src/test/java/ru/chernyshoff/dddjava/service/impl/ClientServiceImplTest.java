package ru.chernyshoff.dddjava.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.chernyshoff.dddjava.dao.repository.ClientRepository;
import ru.chernyshoff.dddjava.dao.repository.OrderRepository;
import ru.chernyshoff.dddjava.domain.Client;
import ru.chernyshoff.dddjava.domain.Order;
import ru.chernyshoff.dddjava.service.ClientService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    private ClientRepository clientRepository;
    private OrderRepository orderRepository;
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        orderRepository = mock(OrderRepository.class);
        clientService = new ClientServiceImpl(clientRepository, orderRepository);
    }

    @Test
    void create_shouldSaveAndReturnClient() {
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Client result = clientService.create("John", "+123", "Street 1");

        verify(clientRepository).save(any(Client.class));
        assertNotNull(result.id());
        assertEquals("John", result.name());
        assertEquals("+123", result.phone());
        assertEquals("Street 1", result.deliveryAddress());
        assertEquals(List.of(), result.orderIds());
    }

    @Test
    void findById_shouldDelegateToRepository() {
        UUID id = UUID.randomUUID();
        Client client = new Client(id, "John", "+123", "Street", List.of());
        when(clientRepository.findById(id)).thenReturn(client);

        Client result = clientService.findById(id);

        assertEquals(client, result);
        verify(clientRepository).findById(id);
    }

    @Test
    void update_shouldDelegateToRepository() {
        Client client = new Client(UUID.randomUUID(), "John", "+123", "Street", List.of());
        when(clientRepository.update(client)).thenReturn(client);

        Client result = clientService.update(client);

        assertEquals(client, result);
        verify(clientRepository).update(client);
    }

    @Test
    void delete_shouldDelegateToRepository() {
        UUID id = UUID.randomUUID();

        clientService.delete(id);

        verify(clientRepository).deleteById(id);
    }

    @Test
    void changeDeliveryAddress_shouldUpdateAddressAndPersist() {
        Client client = new Client(UUID.randomUUID(), "John", "+123", "Old street", List.of());
        when(clientRepository.update(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Client result = clientService.changeDeliveryAddress(client, "New street");

        verify(clientRepository).update(any(Client.class));
        assertEquals("New street", result.deliveryAddress());
        assertEquals(client.id(), result.id());
    }

    @Test
    void addOrder_shouldAppendOrderIdAndPersist() {
        UUID existingOrderId = UUID.randomUUID();
        Client client = new Client(UUID.randomUUID(), "John", "+123", "Street", List.of(existingOrderId));
        UUID newOrderId = UUID.randomUUID();
        when(clientRepository.update(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Client result = clientService.addOrder(client, newOrderId);

        verify(clientRepository).update(any(Client.class));
        assertEquals(List.of(existingOrderId, newOrderId), result.orderIds());
    }

    @Test
    void getOrders_shouldLoadOrdersFromRepository() {
        UUID orderId1 = UUID.randomUUID();
        UUID orderId2 = UUID.randomUUID();
        Client client = new Client(UUID.randomUUID(), "John", "+123", "Street", List.of(orderId1, orderId2));

        Order order1 = new Order(orderId1, client.id(), UUID.randomUUID(), UUID.randomUUID(),
                List.of(), Instant.now(), "CREATED", BigDecimal.TEN);
        Order order2 = new Order(orderId2, client.id(), UUID.randomUUID(), UUID.randomUUID(),
                List.of(), Instant.now(), "CREATED", BigDecimal.ONE);

        when(orderRepository.findById(orderId1)).thenReturn(order1);
        when(orderRepository.findById(orderId2)).thenReturn(order2);

        List<Order> result = clientService.getOrders(client);

        assertEquals(List.of(order1, order2), result);
        verify(orderRepository).findById(orderId1);
        verify(orderRepository).findById(orderId2);
    }
}
