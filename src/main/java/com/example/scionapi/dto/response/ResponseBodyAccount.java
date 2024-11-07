package com.example.scionapi.dto.response;

public record ResponseBodyAccount(

        Long id,

        String accountNumber,

        String accountType,

        String balance,

        String status
) {
}
