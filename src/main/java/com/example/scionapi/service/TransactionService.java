package com.example.scionapi.service;

import com.example.scionapi.dto.request.RequestBodyTransaction;
import com.example.scionapi.dto.response.ResponseBodyTransaction;
import com.example.scionapi.model.Account;
import com.example.scionapi.model.Client;
import com.example.scionapi.model.Transaction;
import com.example.scionapi.repository.AccountRepository;
import com.example.scionapi.repository.ClientRepository;
import com.example.scionapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    //injecao de dependencia para poder usar o metodos do Java Persistence Api (JPA)
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction was not found."));
    }

    public ResponseBodyTransaction createTransaction(RequestBodyTransaction bodyTransaction) {
        //account
        //client
        Account account = accountRepository.findById(bodyTransaction.clientId())
                .orElseThrow(() -> new RuntimeException("Account was not found."));
        Client client = clientRepository.findById(bodyTransaction.clientId())
                .orElseThrow(() -> new RuntimeException("Client " + bodyTransaction.clientId() + "was not found."));
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(bodyTransaction.amount());
        transaction1.setDescription(bodyTransaction.description());
        transaction1.setTransactionDate(bodyTransaction.transactionDate());
        transaction1.setAccount(account);
        transaction1.setClient(client);

        transaction1 = transactionRepository.save(transaction1);

        return new ResponseBodyTransaction(
                transaction1.getId(),
                transaction1.getAmount(),
                transaction1.getTransactionDate(),
                transaction1.getDescription(),
                transaction1.getAccount().getId(),
                transaction1.getClient().getId()
        );
    }

//    public void verifyTransaction() {
//        Transaction transaction = transactionRepository.getById(id);
//    }

    public Transaction updateTransaction(Long id, RequestBodyTransaction bodyTransaction) {
        Transaction transaction1 = findTransactionById(id);
        if(bodyTransaction.transactionDate() != null) {
            transaction1.setTransactionDate(bodyTransaction.transactionDate());
        }
        if(bodyTransaction.description() != null) {
            transaction1.setDescription(bodyTransaction.description());
        }
        if (bodyTransaction.amount() != null) {
            transaction1.setAmount(bodyTransaction.amount());
        }
        return transactionRepository.save(transaction1);
    }

    public Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank with ID " + id + " was not found."));
    }

    public void deleteTransactionById(Long id) {
        Transaction transaction = findTransactionById(id);
        transactionRepository.delete(transaction);
    }


}
