package com.example.scionapi.exception;

public class ClientCpfAlreadyExistsException extends RuntimeException {
    public ClientCpfAlreadyExistsException(String message) {
        super(message);
    }
}
