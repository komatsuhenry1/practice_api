package com.example.scionapi.dto.response;

import java.util.List;

public record ResponseBodyClientAccountBank(

        Long id,

        String name,

        String phone,

        String cpf,

        String email,

        String adress,

        Long account_id,

        Long bank_id

//        List<Long> transactions



) {
}
