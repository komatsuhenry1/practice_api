package com.example.scionapi.service;

import com.example.scionapi.dto.request.RequestBodyClient;
import com.example.scionapi.dto.response.ResponseBodyClient;
import com.example.scionapi.model.Account;
import com.example.scionapi.model.Bank;
import com.example.scionapi.model.Client;
import com.example.scionapi.repository.AccountRepository;
import com.example.scionapi.repository.BankRepository;
import com.example.scionapi.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    //injecao de dependencia para poder usar o metodos do Java Persistence Api (JPA)
    @Autowired
    private ClientRepository clientRepository;
    //injecao para poder pegar os atributos de estrangeiros da tabela bank
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private AccountRepository accountRepository;

    //get all
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    //get by id
    public Client searchClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client " + id + "was not found!"));
    }

    //post
    public ResponseBodyClient createClient(RequestBodyClient bodyClient) {
        verifyClient(bodyClient.cpf());
        Account account = accountRepository.findById(bodyClient.account_id())
                .orElseThrow(() -> new RuntimeException("Account " + bodyClient.account_id() + "was not found!"));
        Bank bank = bankRepository.findById(bodyClient.bank_id())
                .orElseThrow(() -> new EntityNotFoundException("Bank "  + bodyClient.bank_id() +  " was not found!"));

        Client client = new Client();
        client.setName(bodyClient.name());
        client.setAddress(bodyClient.adress());
        client.setCpf(bodyClient.cpf());
        client.setEmail(bodyClient.email());
        client.setPhone(bodyClient.phone());
        client.setAccount(account);
        client.setBank(bank);

        client = clientRepository.save(client);

        return new ResponseBodyClient(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getCpf(),
                client.getEmail(),
                client.getAddress(),
                client.getBank().getId(),
                client.getAccount().getId()
        );
    }

    //verifica se o cpf ja existe na tabela
    public void verifyClient(String cpf) {
        Client client = clientRepository.findByCpf(cpf);
        if(client != null) {
            throw new RuntimeException("Client with cpf (" + cpf + ") already exists!");
        }
    }

    //put
    public Client updateClient(Long id, Client bodyClient) {
        Client client = findClientById(id);
        if (bodyClient.getName() != null) {
            client.setName(bodyClient.getName());
        }
        if (bodyClient.getCpf() != null) {
            client.setEmail(bodyClient.getCpf());
        }
        if (bodyClient.getPhone() != null) {
            client.setPhone(bodyClient.getPhone());
        }
        if (bodyClient.getEmail() != null) {
            client.setEmail(bodyClient.getEmail());
        }
        if (bodyClient.getAddress() != null) {
            client.setAddress(bodyClient.getAddress());
        }

        return clientRepository.save(client);
    }

    //procura por id
    public Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank with ID " + id + " was not found." ));
    }

    public void deleteClient(Long id) {
        Client client = findClientById(id);
        clientRepository.deleteById(client.getId());
    }
}
