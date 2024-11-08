package com.example.scionapi.dto.request;

public record RequestBodyAccount(

        String accountNumber,

        String accountType,

        String balance,

        String status

) {
}
