package com.example.scionapi.dto;

import java.util.List;

public record BodyBank(

        String name,

        String email,

        String phone,

        String address,

        // (Opcional) Lista de IDs dos clientes associados
        List<Long> clientIds
) {
}
