package com.example.scionapi.exception;

public class BankNameAlreadyExistsException extends RuntimeException {
    public BankNameAlreadyExistsException(String message) {
        super(message);
    }
}
