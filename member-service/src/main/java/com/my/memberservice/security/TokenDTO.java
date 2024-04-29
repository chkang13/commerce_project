package com.my.memberservice.security;

import lombok.Builder;
import lombok.Getter;

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

