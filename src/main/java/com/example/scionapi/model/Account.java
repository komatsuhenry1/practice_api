package com.example.scionapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//TODAS RELAÇÕES EM NOSSO ESQUEMA DE BANCO DE DADOS:
//bank - client (um banco tem varios clientes) OK
//client - transaction ( um cliente tem varias transacoes)
//client - account (um cliente tem uma conta)
//account - transaction (uma conta tem varias transacoes) OK

@Setter
@Getter
@Data
@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_id;

    private Integer accountNumber;

    private String accountType;

    private String balance;

    private String status;

    private String email;

    private String password;



    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();


    public Long getId() {
        return account_id;
    }

    public void setId(Long id) {
        this.account_id = account_id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAccountNumber() {return accountNumber;}

    public void setAccountNumber(Integer accountNumber) {this.accountNumber = accountNumber;}

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}
