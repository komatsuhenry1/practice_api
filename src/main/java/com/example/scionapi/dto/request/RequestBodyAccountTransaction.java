package com.example.scionapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record RequestBodyAccountTransaction(

        @Positive(message = "Field 'accountNumber' must be positive.")
        @NotNull(message = "Field 'accountNumber' cannot be null.")
        Integer accountNumber,

        @NotNull(message = "Field 'accountType' cannot be null.")
        @NotBlank(message = "Field 'accountType' cannot be blank.")
        String accountType,

        @NotNull(message = "Field 'balance' cannot be null.")
        @NotBlank(message = "Field 'balance' cannot be blank.")
        String balance,

        @NotNull(message = "Field 'status' cannot be null.")
        @NotBlank(message = "Field 'status' cannot be blank.")
        String status,

        @NotNull(message = "Field 'transactionIds' cannot be null, fill with existing transaction IDS.")
        // lista de ids das transactions
        List<Long> transactionIds

) {
}
