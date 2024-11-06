package com.example.scionapi.repository;

import com.example.scionapi.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
    public Bank findByName(String name);
}
