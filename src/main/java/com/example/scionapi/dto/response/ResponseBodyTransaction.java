package com.example.scionapi.dto.response;

public record ResponseBodyTransaction(

        Long id,

        String amount,

        String transactionDate,

        String description

) {
}
