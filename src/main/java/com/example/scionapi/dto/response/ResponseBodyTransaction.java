package com.example.scionapi.dto.response;

public record ResponseBodyTransaction(

        Long id,

        String amount,

        String transactionDate,

        String description,

        // IDs do cliente e da conta associados
        Long clientId,

        Long accountId

) {
}
