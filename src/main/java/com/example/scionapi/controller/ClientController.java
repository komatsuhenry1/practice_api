package com.example.scionapi.controller;

import com.example.scionapi.dto.request.RequestBodyClient;
import com.example.scionapi.dto.request.RequestBodyClientAccountBank;
import com.example.scionapi.dto.response.ResponseBodyClient;
import com.example.scionapi.dto.response.ResponseBodyClientAccountBank;
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

    //GET
    //passando id
    @GetMapping("/id/{id}")
    public ResponseEntity<Client> getClientbyId(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.searchClientById(id));
    }

    //GET
    //passando name
    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseBodyClient> getClientbyName(@PathVariable String name){
        return ResponseEntity.ok(clientService.searchClientByName(name));
    }

    //POST
    //passando apenas atributos de cliente e FK
    @PostMapping("/full_create")
    public ResponseEntity<ResponseBodyClientAccountBank> createClient(@RequestBody RequestBodyClientAccountBank bodyClient){
        return ResponseEntity.ok(clientService.createClientWithFK(bodyClient));
    }

    //POST
    //passando apenas atributos de cliente
    @PostMapping
    public ResponseEntity<ResponseBodyClient> createClient(@RequestBody RequestBodyClient bodyClient){
        return ResponseEntity.ok(clientService.createClient(bodyClient));
    }

    //PUT
    //passando id para editar algum campo
    @PutMapping("{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return ResponseEntity.ok(clientService.updateClient(id, client));
    }

    //delete passando id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok("Client with id " + id + " removed with sucess!");
    }


}
