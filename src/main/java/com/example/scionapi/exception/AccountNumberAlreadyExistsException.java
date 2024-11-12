package com.example.scionapi.exception;

public class AccountNumberAlreadyExistsException extends RuntimeException {
    public AccountNumberAlreadyExistsException(String message) {
        super(message);
    }
}
