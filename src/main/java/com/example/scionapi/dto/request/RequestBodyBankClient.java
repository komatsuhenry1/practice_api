package com.example.scionapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RequestBodyBankClient(

        @NotBlank(message = "Field 'name' cannot be blank/null and must be unique.")
        String name,

        @Email(message = "Please fill 'email' with a valid format.")
        @NotBlank(message = "Field 'email' cannot be blank/null.")
        String email,

        @NotBlank(message = "Field 'phone' cannot be blank/null.")
        @Size(min = 9, message = "Field 'phone' must have at least 11 characters.")
        String phone,

        @NotBlank(message = "Field 'address' cannot be blank/null.")
        String address,

        @NotNull(message = "Field 'clientIds' cannot be null, fill with existing/unique clients IDS.")
        List<Long> clientIds
) {
}
