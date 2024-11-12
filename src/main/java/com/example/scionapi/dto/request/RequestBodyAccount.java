package com.example.scionapi.dto.request;

import jakarta.validation.constraints.*;

public record RequestBodyAccount(

        @Positive(message = "Field 'accountNumber' must be positive.")
        @NotNull(message = "Field 'accountNumber' cannot be null.")
        Integer accountNumber,

        @NotBlank(message = "Field 'accountType' cannot be blank//null.")
        String accountType,

        @NotBlank(message = "Field 'balance' cannot be blank/null.")
        String balance,

        @NotBlank(message = "Field 'status' cannot be blank/null.")
        String status,

        @NotBlank(message ="Field 'email' cannot be blank/null.")
        @Email(message = "Please fill 'email' with a valid format.")
        String email,

        @NotBlank(message = "Field 'password' cannot be blank/null.")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*",
                message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character")
        String password

) {
}
