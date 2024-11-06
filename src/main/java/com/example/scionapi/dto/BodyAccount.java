package com.example.scionapi.dto;

import java.util.List;

public record BodyAccount(

        String accountNumber,

        String accountType,

        String balance,

        String status,

        // ID do cliente associado
        Long clientId
) {
}
