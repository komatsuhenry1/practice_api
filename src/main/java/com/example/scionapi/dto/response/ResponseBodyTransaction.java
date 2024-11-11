package com.example.scionapi.dto.response;

public record ResponseBodyTransaction(

        String amount,

        String transactionDate,

        String description

) {
}
