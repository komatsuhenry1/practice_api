package com.example.scionapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestBodyClient(

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
        @Email(message = "Please fill 'email' with a valid format.")
        String email,

        @NotBlank(message = "Field 'address' cannot be blank/null.")
        String address

) {
}
