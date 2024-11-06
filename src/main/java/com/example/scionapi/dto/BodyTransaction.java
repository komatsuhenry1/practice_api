package com.example.scionapi.dto;

public record BodyTransaction(

        String amount,

        String transactionDate,

        String description,

        // IDs do cliente e da conta associados
        Long clientId,

        Long accountId
) {
}
