package com.example.rest_example.repository;

import com.example.rest_example.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryClients extends CrudRepository<Client,Integer> {
}
