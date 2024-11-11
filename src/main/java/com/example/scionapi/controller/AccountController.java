package com.example.scionapi.controller;

import com.example.scionapi.dto.request.RequestBodyAccount;
import com.example.scionapi.dto.request.RequestBodyAccountTransaction;
import com.example.scionapi.dto.response.ResponseBodyAccount;
import com.example.scionapi.dto.response.ResponseBodyAccountList;
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

    //get de account com lista de transacoes
    @GetMapping("get_accounts")
    public ResponseEntity<List<Account>> getAllAccount() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    //get de account passando o numero da conta (sem lista de transacoes)
    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<ResponseBodyAccount> getAccountByNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountByAccountNumber(accountNumber));
    }

    //get de account por ai (retorna com lista)
    @GetMapping("{id}")
    public ResponseEntity<ResponseBodyAccountList> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    //post de account (sem passar lista de transacoes)
    @PostMapping
    public ResponseEntity<ResponseBodyAccount> createAccount(@RequestBody RequestBodyAccount bodyAccount) {
        return ResponseEntity.ok(accountService.createAccount(bodyAccount));
    }

    //post de account (passando lista de transacoes)
    @PostMapping("/account_transaction")
    public ResponseEntity<ResponseBodyAccountList> createAccountWithTransactions(@RequestBody RequestBodyAccountTransaction bodyAccountTransaction){
        return ResponseEntity.ok(accountService.createAccountWithTransactions(bodyAccountTransaction));
    }

    //update account
    @PutMapping("{id}")
    public ResponseEntity<ResponseBodyAccount> updateAccount(@PathVariable Long id, @RequestBody Account bodyAccount) {
        return ResponseEntity.ok(accountService.updateAccount(id, bodyAccount));
    }

    //delete account
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account with id (" + id + ") was removed with success!");
    }
}
