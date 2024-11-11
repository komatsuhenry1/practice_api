package com.example.scionapi.dto.response;

import java.util.List;

public record ResponseBodyAccountList(

        Long id,

        String accountNumber,

        String accountType,

        String balance,

        String status,

        List<Long> transactions

) {
}
