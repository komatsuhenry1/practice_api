package com.example.scionapi.dto.request;

import java.util.List;

public record RequestBodyBank(

        String name,

        String email,

        String phone,

        String address
) {
}
