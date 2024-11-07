package com.example.scionapi.service;

import com.example.scionapi.dto.request.RequestBodyAccount;
import com.example.scionapi.dto.response.ResponseBodyAccount;
import com.example.scionapi.model.Account;
import com.example.scionapi.repository.AccountRepository;
import com.example.scionapi.repository.BankRepository;
import com.example.scionapi.repository.ClientRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    //injecao de dependencia para poder usar o metodos do Java Persistence Api (JPA)
    @Autowired
    private AccountRepository accountRepository;

    //injecao de dependencia para poder pegar os atributos estrangeiros da tabela Account
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BankRepository bankRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id); // findById retorna um Optional<Account>
//                .orElseThrow(() -> new RuntimeException("Account was not found!"))
    }

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

//    public void verifyAccount(String number) {
//        Account account = accountRepository.findByAccountNumber(number);
//        if(account != null) {
//            throw new RuntimeException("Account with number " + number + " already exists.");
//        }
//    }

    public Account updateAccount(Long id, Account bodyAccount) {
        Account account = findAccountById(id);
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

    public Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank with ID " + id + " was not found."));
    }

    public void deleteAccount(Long id){
        Account account = findAccountById(id);
        accountRepository.delete(account);
    }
}
