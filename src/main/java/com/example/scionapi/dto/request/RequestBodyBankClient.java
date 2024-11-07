package com.example.scionapi.dto.request;

import java.util.List;

public record RequestBodyBankClient(

        String name,

        String email,

        String phone,

        String address,

        // (Opcional) Lista de IDs dos clientes associados
        List<Long> clientIds
) {
}
