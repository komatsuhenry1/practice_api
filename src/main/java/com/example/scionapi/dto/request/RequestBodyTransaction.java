package com.example.scionapi.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RequestBodyTransaction(

        @NotBlank(message = "Field 'amount' cannot be blank/null")
        String amount,

        @NotBlank(message = "Field 'transactionDate' cannot be blank/null")
        String transactionDate,

        String description

) {

}
