package com.example.scionapi.dto.request;

public record RequestBodyClient(

        String name,

        String phone,

        String cpf,

        String email,

        String adress,

        Long account_id,

        Long bank_id
) {
}
