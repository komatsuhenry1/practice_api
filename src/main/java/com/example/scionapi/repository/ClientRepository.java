package com.example.scionapi.repository;

import com.example.scionapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    public Client findByCpf(String cpf);

    Client findByName(String name);
}
