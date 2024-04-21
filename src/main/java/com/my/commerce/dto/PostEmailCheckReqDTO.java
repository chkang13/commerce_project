package com.my.commerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostEmailCheckReqDTO {
    private String email;
    private String authCode;

    @Builder
    public PostEmailCheckReqDTO(String email, String authCode) {
        this.email = email;
        this.authCode = authCode;
    }
}
