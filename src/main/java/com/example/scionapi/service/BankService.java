package com.example.scionapi.service;


import com.example.scionapi.dto.request.RequestBodyBank;
import com.example.scionapi.dto.request.RequestBodyBankClient;
import com.example.scionapi.dto.response.ResponseBodyBank;
import com.example.scionapi.dto.response.ResponseBodyBankList;
import com.example.scionapi.model.Bank;
import com.example.scionapi.model.Client;
import com.example.scionapi.repository.BankRepository;
import com.example.scionapi.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    //injecao de dependencia para poder usar o metodos do Java Persistence Api (JPA)
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ClientRepository clientRepository;

    //get all
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    //get by name ( retorna sem a lista de client)
    public ResponseBodyBank getBankByName(String name) {
        Bank bank = bankRepository.findByName(name);

        return new ResponseBodyBank(
                bank.getId(),
                bank.getName(),
                bank.getEmail(),
                bank.getPhone(),
                bank.getAddress()
        );
    }

    //get by id ( retorna com lista de client)
    public ResponseBodyBankList getBankById(Long id){
        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bank not found"));

        List<Long> clientIds = bank.getClients().stream()
                .map(Client::getId)
                .toList();

        return new ResponseBodyBankList(
                bank.getId(),
                bank.getName(),
                bank.getEmail(),
                bank.getPhone(),
                bank.getAddress(),
                clientIds
        );
    }

    //post
    public ResponseBodyBankList createBankWithClient(RequestBodyBankClient bodybank) {
        verifyBank(bodybank.name());

        List<Client> clients = clientRepository.findAllById(bodybank.clientIds());

        Bank bank = new Bank();
        bank.setName(bodybank.name());
        bank.setEmail(bodybank.email());
        bank.setPhone(bodybank.phone());
        bank.setAddress(bodybank.address());

        bank.setClients(clients);

        bank = bankRepository.save(bank);

        List<Long> clientIds = clients.stream()
                .map(Client::getId)
                .toList();

        return new ResponseBodyBankList(
                bank.getId(),
                bank.getName(),
                bank.getEmail(),
                bank.getPhone(),
                bank.getAddress(),
                clientIds
        );
    }

    public ResponseBodyBank createBank(RequestBodyBank bodybank) {
        verifyBank(bodybank.name());

        Bank bank = new Bank();
        bank.setName(bodybank.name());
        bank.setEmail(bodybank.email());
        bank.setPhone(bodybank.phone());
        bank.setAddress(bodybank.address());

        return new ResponseBodyBank(
                bank.getId(),
                bank.getName(),
                bank.getEmail(),
                bank.getPhone(),
                bank.getAddress()
        );
    }


    //verifica se esse objeto ja esta salvo no db atraves do nome, caso sim ele retorna uma excessao
    public void verifyBank(String name) {
        Bank bank = bankRepository.findByName(name);
        if(bank != null) { // estiver preenchido
            throw new RuntimeException("Client with name " + name + "already exists!");
        }
    }

    //put
    public Bank updateBank(Long id, Bank bodyBank) {
        Bank bank = findBankById(id);
        if (bodyBank.getName() != null) {
            bank.setName(bodyBank.getName());
        }
        if (bodyBank.getEmail() != null) {
            bank.setEmail(bodyBank.getEmail());
        }
        if (bodyBank.getAddress() != null) {
            bank.setAddress(bodyBank.getAddress());
        }
        if (bodyBank.getPhone() != null) {
            bank.setPhone(bodyBank.getPhone());
        }
        return bankRepository.save(bank);
    }

    public Bank findBankById(Long id) {
        return bankRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank with ID " + id + " was not found."));
    }

    //delete
    public void deleteBank(Long id) {
        Bank bank = findBankById(id);
        bankRepository.deleteById(bank.getId());
    }

}
