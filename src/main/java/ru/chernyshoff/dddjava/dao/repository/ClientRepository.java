package ru.chernyshoff.dddjava.dao.repository;

import ru.chernyshoff.dddjava.domain.Client;

import java.util.List;
import java.util.UUID;

/**
 * DAO-репозиторий для работы с сущностью Client.
 */
public interface ClientRepository {

    Client save(Client client);

    Client update(Client client);

    Client findById(UUID id);

    List<Client> findAll();

    void deleteById(UUID id);
}
