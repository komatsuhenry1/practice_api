package com.example.scionapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//TODAS RELAÇÕES EM NOSSO ESQUEMA DE BANCO DE DADOS:
//bank - client (um banco tem varios clientes)
//client - transaction ( um cliente tem varias transacoes)
//client - account (um cliente tem uma conta)
//account - transaction (uma conta tem varias transacoes)

@Builder
@Data
@Entity
@Table(name = "bank")
@NoArgsConstructor
@AllArgsConstructor
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    //UM banco tem VARIOS clientes
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    //mappedBy = define o atributo na entidade Client que mapeia esse relacionamento
    //cascade = propaga as operações de persistencia do Bank para os Clients
    //remove os clientes órfãos quando nao estao mais associados a nenhum cliente
    @JsonIgnore // quando eu der um get de bank, nao ira retornar uma lista de client no json
    private List<Client> clients = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
