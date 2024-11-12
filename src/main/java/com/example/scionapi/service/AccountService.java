package com.example.scionapi.service;

import com.example.scionapi.dto.request.RequestBodyAccount;
import com.example.scionapi.dto.request.RequestBodyAccountTransaction;
import com.example.scionapi.dto.response.ResponseBodyAccount;
import com.example.scionapi.dto.response.ResponseBodyAccountList;
import com.example.scionapi.exception.AccountNumberAlreadyExistsException;
import com.example.scionapi.model.Account;
import com.example.scionapi.model.Transaction;
import com.example.scionapi.repository.AccountRepository;
import com.example.scionapi.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

    @Service
public class AccountService {

    //injecao de dependencia para poder usar o metodos do Java Persistence Api (JPA)
    @Autowired
    private AccountRepository accountRepository;

    //injecao de dependencia para poder pegar os atributos estrangeiros da tabela Account
    @Autowired
    private TransactionRepository transactionRepository;

    //GET
    // pega todas as contas
    public List<ResponseBodyAccount> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<ResponseBodyAccount> responseBodyAccounts = new ArrayList<>();

        for(Account account : accounts) {
            responseBodyAccounts.add(new ResponseBodyAccount(
                    account.getId(),
                    account.getAccountNumber(),
                    account.getAccountType(),
                    account.getBalance(),
                    account.getStatus(),
                    account.getEmail(),
                    account.getPassword()
            ));
        }

        return responseBodyAccounts;
    }

    //GET
    //get account passando id (retorna com lista de transacoes)
    public ResponseBodyAccountList getAccountById(Long id) {
        Account account = accountRepository.findById(id)// findById retorna um Optional<Account>
                .orElseThrow(() -> new EntityNotFoundException("Account (" + id + ") was not found!"));

        List<Long> transactionIds = account.getTransactions().stream()
                .map(Transaction::getId)
                .toList();

        return new ResponseBodyAccountList(
                account.getAccount_id(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                account.getEmail(),
                account.getPassword(),
                transactionIds
        );
    }

    //GET
    //pega conta pelo numero (accountNumber) / retorna sem lista de accountId
    public ResponseBodyAccount getAccountByAccountNumber(Integer accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        return new ResponseBodyAccount(
                account.getAccount_id(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                account.getEmail(),
                account.getPassword()
        );
    }

    //POST
    //criar account (sem passar transactions)
    public ResponseBodyAccount createAccount(RequestBodyAccount bodyAccount) {
        verifyAccount(bodyAccount.accountNumber());

        Account account = new Account();
        account.setAccountNumber(bodyAccount.accountNumber());
        account.setAccountType(bodyAccount.accountType());
        account.setBalance(bodyAccount.balance());
        account.setStatus(bodyAccount.status());
        account.setEmail(bodyAccount.email());
        account.setPassword(bodyAccount.password());

        account = accountRepository.save(account);

        return new ResponseBodyAccount(
                account.getAccount_id(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                account.getEmail(),
                account.getPassword()
        );
    }

    //POST
    //criar account (passando lista com transactionsIds
    public ResponseBodyAccountList createAccountWithTransactions(RequestBodyAccountTransaction bodyAccountTransaction) {
        verifyAccount(bodyAccountTransaction.accountNumber());

        List<Transaction> transactions = transactionRepository.findAllById(bodyAccountTransaction.transactionIds());

        System.out.println("All transactions found: " + transactions.size());

//        verifyAccount(bodyAccountTransaction.accountNumber());


//        if (transactions.size() != transactionIds.size()) {
//            throw new IllegalArgumentException("Alguns IDs de transações não foram encontrados no banco de dados.");
//        }

        Account account = new Account();
        account.setAccountNumber(bodyAccountTransaction.accountNumber());
        account.setAccountType(bodyAccountTransaction.accountType());
        account.setBalance(bodyAccountTransaction.balance());
        account.setStatus(bodyAccountTransaction.status());
        account.setEmail(bodyAccountTransaction.email());
        account.setPassword(bodyAccountTransaction.password());

        account.setTransactions(transactions);

        account = accountRepository.save(account);

        List<Long> savedTransactionIds = transactions.stream()
                .map(Transaction::getId)
                .toList();

        return new ResponseBodyAccountList(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                account.getEmail(),
                account.getPassword(),
                savedTransactionIds
        );
    }

    public void verifyAccount(int number) {
        Account account = accountRepository.findByAccountNumber(number);
        if(account != null) {
            throw new AccountNumberAlreadyExistsException("Account with number (" + number + ") already exists.");
        }
    }

    //PUT
    //edita cliente
    public ResponseBodyAccount updateAccount(Long id, Account bodyAccount) {
        Account account = findAccountById(id); // chama metodo para achar objeto account pelo id
        if(bodyAccount.getAccountType() != null) {
            account.setAccountType(bodyAccount.getAccountType());
        }
        if(bodyAccount.getAccountNumber() != null) {
            account.setAccountNumber(bodyAccount.getAccountNumber());
        }
        if(bodyAccount.getBalance() != null) {
            account.setBalance(bodyAccount.getBalance());
        }
        if(bodyAccount.getStatus() != null) {
            account.setStatus(bodyAccount.getStatus());
        }
        return new ResponseBodyAccount(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                account.getEmail(),
                account.getPassword()
        );
    }

    //metodo para achar objeto account pelo id
    public Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank with (" + id + ") was not found."));
    }

    //DELETE de account
    //deleta account pelo id
    public void deleteAccount(Long id){
        Account account = findAccountById(id);
        accountRepository.delete(account);
    }
}
