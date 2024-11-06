package com.example.scionapi.repository;

import com.example.scionapi.model.Account;
import com.example.scionapi.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long>{
    public Account findByAccountNumber(String numberAccount);
}
