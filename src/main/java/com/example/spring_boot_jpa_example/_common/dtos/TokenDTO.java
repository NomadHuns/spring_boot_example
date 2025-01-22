package com.example.spring_boot_jpa_example._common.dtos;

import lombok.Getter;

@Getter
public class TokenDTO {
    private final String accessToken;
    private final String refreshToken;

    public TokenDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
