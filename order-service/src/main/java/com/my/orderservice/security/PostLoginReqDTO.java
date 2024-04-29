package com.my.orderservice.security;

import lombok.Getter;

@Getter
public class PostLoginReqDTO {
    private String email;
    private String password;
}
