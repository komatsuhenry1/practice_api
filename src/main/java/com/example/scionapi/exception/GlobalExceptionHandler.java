package com.example.scionapi.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Classe para tratar exceções globalmente
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Exceção para validação de campos (MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();

        // Iterar sobre os erros dos campos
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            // Adiciona os erros ao map, onde a chave é o nome do campo e o valor é a mensagem de erro
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()); // Field = chave | DefaultMessage = mensagem de erro (DTO)
        }

        // Retorna a resposta com o status de BAD_REQUEST e as mensagens de erro no corpo
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    // Exceção para quando ocorre uma violação de restrição de dados
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errorMap = new HashMap<>();

        // Iterar sobre as violações de restrição
        ex.getConstraintViolations().forEach(violation -> {
            errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
        });

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientCpfAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleClientCpfAlreadyExistsException(ClientCpfAlreadyExistsException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BankNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleBankNameAlreadyExistsException(BankNameAlreadyExistsException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNumberAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAccountNumberAlreadyExistsException(AccountNumberAlreadyExistsException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    // Outras exceções podem ser tratadas aqui...
}
