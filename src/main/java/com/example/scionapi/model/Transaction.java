package com.example.scionapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODAS RELAÇÕES EM NOSSO ESQUEMA DE BANCO DE DADOS:
//bank - client (um banco tem varios clientes)
//client - transaction ( um cliente tem varias transacoes)
//client - account (um cliente tem uma conta)
//account - transaction (uma conta tem varias transacoes)

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String amount;

    private String transactionDate;

    private String description;

    //chave estrangeira na tabela transaction, que vem do id de client
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getAmount() {return amount;}

    public void setAmount(String amount) {this.amount = amount;}

    public String getTransactionDate() {return transactionDate;}

    public void setTransactionDate(String transactionDate) {this.transactionDate = transactionDate;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
