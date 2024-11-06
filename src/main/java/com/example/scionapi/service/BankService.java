package com.example.scionapi.service;


import com.example.scionapi.dto.BodyBank;
import com.example.scionapi.model.Bank;
import com.example.scionapi.model.Client;
import com.example.scionapi.repository.BankRepository;
import com.example.scionapi.repository.ClientRepository;
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

    //get by name | não é mais usado, agora é procurado por ID
//    public Bank getBankByName(String name){
//        return bankRepository.findByName(name);
//    }

    //get by id
    public Bank getBankById(Long id){
        return bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank was not found.")); //joga excecao se n achar
    }

    //post
    public Bank createBank(BodyBank bodybank){
        verifyBank(bodybank.name());
        Bank bank = new Bank();
        bank.setName(bodybank.name());
        bank.setEmail(bodybank.email());
        bank.setPhone(bodybank.phone());
        bank.setAddress(bodybank.address());

        return bankRepository.save(bank);
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
