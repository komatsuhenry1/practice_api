package com.example.scionapi.service;

import com.example.scionapi.dto.request.RequestBodyClient;
import com.example.scionapi.dto.request.RequestBodyClientAccountBank;
import com.example.scionapi.dto.response.ResponseBodyClient;
import com.example.scionapi.dto.response.ResponseBodyClientAccountBank;
import com.example.scionapi.exception.ClientCpfAlreadyExistsException;
import com.example.scionapi.model.Account;
import com.example.scionapi.model.Bank;
import com.example.scionapi.model.Client;
import com.example.scionapi.model.Transaction;
import com.example.scionapi.repository.AccountRepository;
import com.example.scionapi.repository.BankRepository;
import com.example.scionapi.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    //injecao de dependencia para poder usar o metodos do Java Persistence Api (JPA)
    @Autowired
    private ClientRepository clientRepository;
    //injecao para poder pegar as FK da tabela bank
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private AccountRepository accountRepository;

    //get all
    public List<ResponseBodyClient> getAllClient() {
        List<Client> clients = clientRepository.findAll(); // cria uma lista de clients
        List<ResponseBodyClient> responseBodyClients = new ArrayList<>(); // uma lista de ResponseBodyClient

        //para cada client em List<Client> adiciona na List<ResponseBodyClient>
        for (Client client : clients) {
            responseBodyClients.add(new ResponseBodyClient(
                    client.getId(),
                    client.getName(),
                    client.getPhone(),
                    client.getCpf(),
                    client.getEmail(),
                    client.getAddress()
            ));
        }

        return responseBodyClients; // retorna a response
    }

    //get by id
    public ResponseBodyClient searchClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client (" + id + ") was not not found"));

        List<Long> transactionIds = client.getTransactions().stream()
                .map(Transaction::getId)
                .toList();

        return new ResponseBodyClient(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getPhone(),
                client.getEmail(),
                client.getAddress()
        );
    }

    //get by name
    //retorna resposta basica (sem lista e campos de outras tabelas)
    public ResponseBodyClient searchClientByName(String name) {
        Client client = clientRepository.findByName(name);

        return new ResponseBodyClient(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getCpf(),
                client.getEmail(),
                client.getAddress()
        );
    }

    //POST
    //passando client
    public ResponseBodyClientAccountBank createClientWithFK(RequestBodyClientAccountBank bodyClient) {
        verifyClient(bodyClient.cpf());
        Account account = accountRepository.findById(bodyClient.account_id())
                .orElseThrow(() -> new RuntimeException("Account " + bodyClient.account_id() + "was not found!"));
        Bank bank = bankRepository.findById(bodyClient.bank_id())
                .orElseThrow(() -> new EntityNotFoundException("Bank "  + bodyClient.bank_id() +  " was not found!"));
//        Client client = clientRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Client (" + id + ") was not not found"));
//
//
//        List<Long> transactionIds = client.getTransactions().stream()
//                .map(Transaction::getId)
//                .toList();

        Client client = new Client();
        client.setName(bodyClient.name());
        client.setAddress(bodyClient.address());
        client.setCpf(bodyClient.cpf());
        client.setEmail(bodyClient.email());
        client.setPhone(bodyClient.phone());
        client.setAccount(account);
        client.setBank(bank);

        client = clientRepository.save(client);

        return new ResponseBodyClientAccountBank(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getCpf(),
                client.getEmail(),
                client.getAddress(),
                client.getAccount().getId(),
                client.getBank().getId()
//                transactionIds

        );
    }

    //POST
    //passando client account id e bank id
    public ResponseBodyClient createClient(RequestBodyClient bodyClient) {
        verifyClient(bodyClient.cpf());

        Client client = new Client();
        client.setName(bodyClient.name());
        client.setPhone(bodyClient.phone());
        client.setCpf(bodyClient.cpf());
        client.setEmail(bodyClient.email());
        client.setAddress(bodyClient.address());

        client = clientRepository.save(client);

        return new ResponseBodyClient(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getCpf(),
                client.getAddress(),
                client.getEmail()
        );
    }

    //metodo de verificacao
    //verifica se o cpf ja existe na tabela
    public void verifyClient(String cpf) {
        Client client = clientRepository.findByCpf(cpf);
        if(client != null) {
            throw new ClientCpfAlreadyExistsException("Client with cpf (" + cpf + ") already exists!");
        }
    }

    //PUT
    //editar algum campo (passando id)
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

    //metodo de verificacao
    //procura por id
    public Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank with ID " + id + " was not found." ));
    }

    //DELETE
    //deletar cliente (passando id)
    public void deleteClient(Long id) {
        Client client = findClientById(id);
        clientRepository.deleteById(client.getId());
    }
}
