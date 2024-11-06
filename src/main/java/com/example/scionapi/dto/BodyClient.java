package com.example.scionapi.dto;

import java.util.List;

public record BodyClient(

        String name,

        String phone,

        String cpf,

        String email,

        String adress,

        // ID da conta associada
        Long accountId
) {
}
