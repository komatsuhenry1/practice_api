package com.example.scionapi.service;

import com.example.scionapi.dto.request.RequestBodyAccount;
import com.example.scionapi.dto.request.RequestBodyAccountTransaction;
import com.example.scionapi.dto.response.ResponseBodyAccount;
import com.example.scionapi.dto.response.ResponseBodyAccountList;
import com.example.scionapi.model.Account;
import com.example.scionapi.model.Transaction;
import com.example.scionapi.repository.AccountRepository;
import com.example.scionapi.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
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
                transactionIds
        );
    }

    //GET
    //pega conta pelo numero (accountNumber) / retorna sem lista de accountId
    public ResponseBodyAccount getAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        return new ResponseBodyAccount(
                account.getAccount_id(),
                account.getAccountType(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getStatus()
        );
    }

    //POST
    //criar account (sem passar transactions)
    public ResponseBodyAccount createAccount(RequestBodyAccount bodyAccount) {
        //        verifyAccount(bodyAccount.accountNumber());

        Account account = new Account();
        account.setAccountNumber(bodyAccount.accountNumber());
        account.setAccountType(bodyAccount.accountType());
        account.setBalance(bodyAccount.balance());
        account.setStatus(bodyAccount.status());

        account = accountRepository.save(account);

        return new ResponseBodyAccount(
                account.getAccount_id(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus()
        );
    }

    //POST
    //criar account (passando lista com transactionsIds
    public ResponseBodyAccountList createAccountWithTransactions(RequestBodyAccountTransaction bodyAccountTransaction) {
        verifyAccount(bodyAccountTransaction.accountNumber());

        List<Transaction> transactions = transactionRepository.findAllById(bodyAccountTransaction.transactionIds());

        Account account = new Account();
        account.setAccountNumber(bodyAccountTransaction.accountNumber());
        account.setAccountType(bodyAccountTransaction.accountType());
        account.setBalance(bodyAccountTransaction.balance());
        account.setStatus(bodyAccountTransaction.status());

        account.setTransactions(transactions);

        account = accountRepository.save(account);

        List<Long> transactionIds = transactions.stream()
                .map(Transaction::getId)
                .toList();

        return new ResponseBodyAccountList(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getStatus(),
                transactionIds
        );
    }

    public void verifyAccount(String number) {
        Account account = accountRepository.findByAccountNumber(number);
        if(account != null) {
            throw new RuntimeException("Account with number " + number + " already exists.");
        }
    }

    //PUT
    //edita cliente
    public Account updateAccount(Long id, Account bodyAccount) {
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
        return accountRepository.save(account);
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
