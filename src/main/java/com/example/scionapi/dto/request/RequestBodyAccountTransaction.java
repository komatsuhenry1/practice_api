package com.example.scionapi.dto.request;

import java.util.List;

public record RequestBodyAccountTransaction(

        String accountNumber,

        String accountType,

        String balance,

        String status,

        // lista de ids das transactions
        List<Long> transactionIds

) {
}
