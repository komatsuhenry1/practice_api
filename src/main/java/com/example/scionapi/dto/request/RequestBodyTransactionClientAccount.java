package com.example.scionapi.dto.request;

public record RequestBodyTransactionClientAccount(

        String amount,

        String transactionDate,

        String description,

        // IDs do cliente e da conta associados
        Long clientId,

        Long accountId
) {
}
