package com.my.commerce.security;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
@Getter
public class TokenDTO {
    private String grantType;
    private String accessToken;

    @Builder
    public TokenDTO(String grantType, String accessToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
    }
}

