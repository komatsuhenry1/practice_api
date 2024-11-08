package com.example.scionapi.controller;

import com.example.scionapi.dto.request.RequestBodyBank;
import com.example.scionapi.dto.request.RequestBodyBankClient;
import com.example.scionapi.dto.response.ResponseBodyBank;
import com.example.scionapi.dto.response.ResponseBodyBankList;
import com.example.scionapi.model.Bank;
import com.example.scionapi.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    //get all banks
    @GetMapping("/get_banks")
    public ResponseEntity<List<Bank>> getBanks() {
        return ResponseEntity.ok(bankService.getAllBanks());
    }

    //get de bank passando name
    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseBodyBank> getBankByName(@PathVariable String name){
        return ResponseEntity.ok(bankService.getBankByName(name));
    }

    //get bank por id
    @GetMapping("{bank_id}")
    public ResponseEntity<ResponseBodyBankList> getBankById(@PathVariable Long bank_id) {
        return ResponseEntity.ok(bankService.getBankById(bank_id));
    }

    //post passando lista de id
    @PostMapping("/bank_client")
    public ResponseEntity<ResponseBodyBankList> createBankWithClient(@RequestBody RequestBodyBankClient bodyBank) {
        return ResponseEntity.ok(bankService.createBankWithClient(bodyBank));
    }

    //post sem lista de clientes
    @PostMapping
    public ResponseEntity<ResponseBodyBank> createBank(@RequestBody RequestBodyBank bodyBank) {
        return ResponseEntity.ok(bankService.createBank(bodyBank));
    }

    //update
    @PutMapping("{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody Bank bank) {
        return ResponseEntity.ok(bankService.updateBank(id, bank));
    }

    //deletar client
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBank(@PathVariable Long id) {
        bankService.deleteBank(id);
        return ResponseEntity.ok("Bank with id " + id + " deleted with success!");
    }

}
