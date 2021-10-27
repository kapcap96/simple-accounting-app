package com.example.rest_example.service;

import com.example.rest_example.model.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
@Service
public class ClientServiceImpl implements ClientService {
    // Хранилище клиентов
    private static final Map<Integer, Client> CLIENT_REPOSITORY_MAP = new HashMap<>();

    // Переменная для генерации ID клиента
    private static final AtomicInteger CLIENT_ID_HOLDER = new AtomicInteger();

    @Override
    public void create(Client client) {
        final int clientId = CLIENT_ID_HOLDER.incrementAndGet();
        client.setId(clientId);
        CLIENT_REPOSITORY_MAP.put(clientId, client);
    }

    @Override
    public List<Client> readALL() {
        return new ArrayList<>(CLIENT_REPOSITORY_MAP.values());
    }

    @Override
    public Client read(int id) {
        return CLIENT_REPOSITORY_MAP.get(id);
    }

    @Override
    public boolean update(Client client, int id) {
        if (CLIENT_REPOSITORY_MAP.containsKey(id)) {    //containsKey проверяет содержится ли данный ID
            client.setId(id);
            CLIENT_REPOSITORY_MAP.put(id, client);      // перезапись ключа
            return true;
        }

        return false;

    }

    @Override
    public boolean delete(int id) {                          // remove удаления ключ, значения
        return CLIENT_REPOSITORY_MAP.remove(id) != null;
    }
}
