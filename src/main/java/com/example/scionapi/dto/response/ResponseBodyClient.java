package com.example.scionapi.dto.response;

public record ResponseBodyClient(

        Long id,

        String name,

        String phone,

        String cpf,

        String email,

        String address

) {
}
