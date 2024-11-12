package com.example.scionapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestBodyClientAccountBank(

        @NotBlank(message = "Field 'name' cannot be blank/null.")
        String name,

        @NotBlank(message = "Field 'phone' cannot be blank/null.")
        @Size(min = 9, message = "Field 'phone' must have at least 9 characters.")
        String phone,

        @NotBlank(message = "Cannot be blank and must be unique.")
        @Size(min = 11, max = 11, message = "CPF must have exactly 11 characters.")
        @Pattern(regexp = "\\d{11}", message = "CPF must contain only numbers.")
        String cpf,

        @NotBlank(message = "Field 'email' cannot be blank/null.")
        String email,

        @NotBlank(message = "Field 'address' cannot be blank/null.")
        String address,

        @NotNull(message = "Field 'account_id' cannot be null and must be unique.")
        Long account_id,

        @NotNull(message = "Field 'bank_id' cannot be null and must be unique.")
        Long bank_id

) {
}
