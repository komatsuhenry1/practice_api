package com.example.scionapi.service;


import com.example.scionapi.dto.request.RequestBodyBank;
import com.example.scionapi.dto.request.RequestBodyBankClient;
import com.example.scionapi.dto.response.ResponseBodyBank;
import com.example.scionapi.dto.response.ResponseBodyBankList;
import com.example.scionapi.exception.BankNameAlreadyExistsException;
import com.example.scionapi.model.Bank;
import com.example.scionapi.model.Client;
import com.example.scionapi.repository.BankRepository;
import com.example.scionapi.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankService {

    //injecao de dependencia para poder usar o metodos do Java Persistence Api (JPA)
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ClientRepository clientRepository;

    //GET
    //get all
    public List<ResponseBodyBank> getAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        List<ResponseBodyBank> responseBodyBanks = new ArrayList<>();

        //para cada bank em List<Bank> adiciona na List<ResponseBodyBank>
        for (Bank bank : banks) {
            responseBodyBanks.add(new ResponseBodyBank(
                    bank.getId(),
                    bank.getName(),
                    bank.getEmail(),
                    bank.getPhone(),
                    bank.getAddress()
            ));
        }
        return responseBodyBanks;
    }

    //GET
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

    //GET
    //get by id ( retorna com lista de client)
    public ResponseBodyBankList getBankById(Long id){
        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bank " + id + " was  found"));

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

    //POST
    //cria bank passando lista de client
    public ResponseBodyBankList createBankWithClient(RequestBodyBankClient requestBodyBankClient) {
        verifyBank(requestBodyBankClient.name());

        List<Client> clients = clientRepository.findAllById(requestBodyBankClient.clientIds());

        Bank bank = new Bank();
        bank.setName(requestBodyBankClient.name());
        bank.setEmail(requestBodyBankClient.email());
        bank.setPhone(requestBodyBankClient.phone());
        bank.setAddress(requestBodyBankClient.address());

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

        bank = bankRepository.save(bank);

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
            throw new BankNameAlreadyExistsException("Client with name (" + name + ") already exists!");
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
