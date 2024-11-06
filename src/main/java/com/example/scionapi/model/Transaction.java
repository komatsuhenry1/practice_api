package com.example.scionapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODAS RELAÇÕES EM NOSSO ESQUEMA DE BANCO DE DADOS:
//bank - client (um banco tem varios clientes)
//client - transaction ( um cliente tem varias transacoes)
//client - account (um cliente tem varias conta)
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

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getAmount() {return amount;}

    public void setAmount(String amount) {this.amount = amount;}

    public String getTransactionDate() {return transactionDate;}

    public void setTransactionDate(String transactionDate) {this.transactionDate = transactionDate;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}
