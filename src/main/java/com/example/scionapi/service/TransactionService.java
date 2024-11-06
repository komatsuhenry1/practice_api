package com.example.scionapi.service;

import com.example.scionapi.dto.BodyTransaction;
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

    public Transaction createTransaction(BodyTransaction bodyTransaction) {

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(bodyTransaction.amount());
        transaction1.setDescription(bodyTransaction.description());
        transaction1.setTransactionDate(bodyTransaction.transactionDate());

        return transactionRepository.save(transaction1);
    }

//    public void verifyTransaction() {
//        Transaction transaction = transactionRepository.getById(id);
//    }

    public Transaction updateTransaction(Long id, BodyTransaction bodyTransaction) {
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
