package com.example.scionapi.dto.request;

public record RequestBodyTransaction(

        String amount,

        String transactionDate,

        String description

) {

}
