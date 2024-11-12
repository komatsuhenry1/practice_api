package com.example.scionapi.controller;

import com.example.scionapi.dto.request.RequestBodyTransaction;
import com.example.scionapi.dto.request.RequestBodyTransactionClientAccount;
import com.example.scionapi.dto.response.ResponseBodyTransaction;
import com.example.scionapi.dto.response.ResponseBodyTransactionClientAccount;
import com.example.scionapi.model.Transaction;
import com.example.scionapi.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    //GET
    //todos
    @GetMapping
    public ResponseEntity<List<ResponseBodyTransaction>> getTransaction() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    //GET
    //por id
    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseBodyTransaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    //GET
    //por transactionDate
    @GetMapping("/transactionDate/{transactionDate}")
    public ResponseEntity<ResponseBodyTransaction> searchTransactionByDate(@PathVariable String transactionDate) {
        return ResponseEntity.ok(transactionService.searchTransactionByDate(transactionDate));
    }


    //POST
    //passando client id e account id
    @PostMapping("/transaction_client_account")
    public ResponseEntity<ResponseBodyTransactionClientAccount> createTransactionWithClientAndAccount(@RequestBody @Valid RequestBodyTransactionClientAccount bodyTransactionClientAccount) {
        return ResponseEntity.ok(transactionService.createTransactionWithClientAndAccount(bodyTransactionClientAccount));
    }

    //POST
    //passando apenas atributos de transaction
    @PostMapping
    public ResponseEntity<ResponseBodyTransaction> createTransaction(@RequestBody @Valid RequestBodyTransaction bodyTransaction) {
        return ResponseEntity.ok(transactionService.createTransaction(bodyTransaction));
    }

    //PUT
    //editar passando id
    @PutMapping("{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody RequestBodyTransactionClientAccount bodyTransaction) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, bodyTransaction));
    }

    //DELETE
    //deletar passando id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id){
        transactionService.deleteTransactionById(id);
        return ResponseEntity.ok("Transaction with id " + id + " deleted.");
    }
}
