package com.example.scionapi.dto.response;

import java.util.List;

public record ResponseBodyAccountList(

        Long id,

        Integer accountNumber,

        String accountType,

        String balance,

        String status,

        String email,

        String password,

        List<Long> transactions

) {
}
