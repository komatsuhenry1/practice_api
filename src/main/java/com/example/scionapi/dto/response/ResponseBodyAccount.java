package com.example.scionapi.dto.response;

public record ResponseBodyAccount(

        Long id,

        Integer accountNumber,

        String accountType,

        String balance,

        String status
) {
}
