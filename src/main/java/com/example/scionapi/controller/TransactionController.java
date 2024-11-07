package com.example.scionapi.controller;

import com.example.scionapi.dto.request.RequestBodyTransaction;
import com.example.scionapi.dto.response.ResponseBodyTransaction;
import com.example.scionapi.model.Transaction;
import com.example.scionapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransaction() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseBodyTransaction> createTransaction(@RequestBody RequestBodyTransaction bodyTransaction) {
        return ResponseEntity.ok(transactionService.createTransaction(bodyTransaction));
    }

    @PutMapping("{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody RequestBodyTransaction bodyTransaction) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, bodyTransaction));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id){
        transactionService.deleteTransactionById(id);
        return ResponseEntity.ok("Transaction with id " + id + " deleted.");
    }
}
