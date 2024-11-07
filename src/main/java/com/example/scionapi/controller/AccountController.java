package com.example.scionapi.controller;

import com.example.scionapi.dto.request.RequestBodyAccount;
import com.example.scionapi.dto.response.ResponseBodyAccount;
import com.example.scionapi.model.Account;
import com.example.scionapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccount() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Account>> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseBodyAccount> createAccount(@RequestBody RequestBodyAccount bodyAccount) {
        return ResponseEntity.ok(accountService.createAccount(bodyAccount));
    }

    @PutMapping("{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account bodyAccount) {
        return ResponseEntity.ok(accountService.updateAccount(id, bodyAccount));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account with id " + id + " was removed with success!");

    }
}
