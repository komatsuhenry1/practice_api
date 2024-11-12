package com.example.scionapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestBodyTransactionClientAccount(

        @NotBlank(message = "Field 'amount' cannot be blank/null")
        String amount,

        @NotBlank(message = "Field 'transactionDate' cannot be blank/null")
        String transactionDate,

        String description,

        @NotNull(message = "Field 'clientId' cannot be null and must be unique.")
        Long clientId,

        @NotNull(message = "Field 'accountId' cannot be null and must be unique.")
        Long accountId
) {
}
