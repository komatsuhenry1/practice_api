package com.example.scionapi.service;

import com.example.scionapi.dto.request.RequestBodyTransaction;
import com.example.scionapi.dto.request.RequestBodyTransactionClientAccount;
import com.example.scionapi.dto.response.ResponseBodyTransaction;
import com.example.scionapi.dto.response.ResponseBodyTransactionClientAccount;
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

    //GET all transaction
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    //GET transaction por id
    public ResponseBodyTransaction getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction (" + id + ") was not found"));

        return new ResponseBodyTransaction(
          transaction.getAmount(),
          transaction.getTransactionDate(),
          transaction.getDescription()
        );
    }

    //GET transaction por transactionDate
    public ResponseBodyTransaction searchTransactionByDate(String transactionDate) {
        Transaction transaction = transactionRepository.findByTransactionDate(transactionDate);

        return new ResponseBodyTransaction(
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getDescription()
        );
    }

    //POST
    //passando ID de client e de account
    public ResponseBodyTransactionClientAccount createTransactionWithClientAndAccount(RequestBodyTransactionClientAccount bodyTransaction) {
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

        return new ResponseBodyTransactionClientAccount(
                transaction1.getId(),
                transaction1.getAmount(),
                transaction1.getTransactionDate(),
                transaction1.getDescription(),
                transaction1.getAccount().getId(),
                transaction1.getClient().getId()
        );
    }

    //POST
    //passando apenas atributos de Transaction
    public ResponseBodyTransaction createTransaction(RequestBodyTransaction bodyTransaction){

//        Account account = accountRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Account was not found."))

        Transaction transaction = new Transaction();
        transaction.setAmount(bodyTransaction.amount());
        transaction.setDescription(bodyTransaction.description());
        transaction.setTransactionDate(bodyTransaction.transactionDate());
//        transaction.setAccount(account);

        transaction = transactionRepository.save(transaction);

        return new ResponseBodyTransaction(
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getDescription()

        );
    }

    //PUT
    //editar algum campo de transaction
    public Transaction updateTransaction(Long id, RequestBodyTransactionClientAccount bodyTransaction) {
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

    //método de verificacao
    //caso id ja exista lança uma exception
    public Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank with ID " + id + " was not found."));
    }

    //DELETE
    //deleta registro de transacao passando o id
    public void deleteTransactionById(Long id) {
        Transaction transaction = findTransactionById(id);
        transactionRepository.delete(transaction);
    }


}
