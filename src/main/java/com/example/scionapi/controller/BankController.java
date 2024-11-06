package com.example.scionapi.controller;

import com.example.scionapi.dto.BodyBank;
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

    @GetMapping
    public ResponseEntity<List<Bank>> getBanks() {
        return ResponseEntity.ok(bankService.getAllBanks());
    }

//    @GetMapping("{name}")
//    public ResponseEntity<Bank> getBankByName(@PathVariable String name){
//        return ResponseEntity.ok(bankService.getBankByName(name));
//    }

    @GetMapping("{bank_id}")
    public ResponseEntity<Bank> getBankById(@PathVariable Long bank_id) {
        return ResponseEntity.ok(bankService.getBankById(bank_id));
    }

    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody BodyBank bodyBank) {
        return ResponseEntity.ok(bankService.createBank(bodyBank));
    }

    @PutMapping("{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody Bank bank) {
        return ResponseEntity.ok(bankService.updateBank(id, bank));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBank(@PathVariable Long id) {
        bankService.deleteBank(id);
        return ResponseEntity.ok("Bank with id " + id + " deleted with success!");
    }

}
