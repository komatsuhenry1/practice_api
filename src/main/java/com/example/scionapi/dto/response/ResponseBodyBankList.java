package com.example.scionapi.dto.response;

import java.util.List;

public record ResponseBodyBankList(

        Long id,

        String name,

        String email,

        String phone,

        String address,

        // (Opcional) Lista de IDs dos clientes associados
        List<Long> clientIds // retorna uma lista apenas com os ids, na requisicao
) {
}
