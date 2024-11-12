package com.example.scionapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RequestBodyAccount(

        @Positive(message = "Field 'accountNumber' must be positive.")
        @NotNull(message = "Field 'accountNumber' cannot be null.")
        Integer accountNumber,

        @NotBlank(message = "Field 'accountType' cannot be blank//null.")
        String accountType,

        @NotBlank(message = "Field 'balance' cannot be blank/null.")
        String balance,

        @NotBlank(message = "Field 'status' cannot be blank/null.")
        String status

) {
}
