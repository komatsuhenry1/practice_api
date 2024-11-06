package com.example.scionapi.controller;

import com.example.scionapi.dto.BodyClient;
import com.example.scionapi.model.Client;
import com.example.scionapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClient() {
        return ResponseEntity.ok(clientService.getAllClient());
    }

    @GetMapping("{id}")
    public ResponseEntity<Client> getClientbyId(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.searchClientById(id));
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody BodyClient bodyClient){
        return ResponseEntity.ok(clientService.createClient(bodyClient));
    }

    @PutMapping("{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return ResponseEntity.ok(clientService.updateClient(id, client));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok("Client with id " + id + " removed with sucess!");
    }


}
