package com.my.commerce.dto;

import com.my.commerce.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostMemberReqDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;

    @Builder
    public PostMemberReqDTO(String name, String email, String password, String phone, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public static Member toEntity(PostMemberReqDTO postMemberReqDTO) {
        return Member.builder()
                .name(postMemberReqDTO.getName())
                .email(postMemberReqDTO.getEmail())
                .password(postMemberReqDTO.getPassword())
                .phone(postMemberReqDTO.getPhone())
                .address(postMemberReqDTO.getAddress())
                .build();
    }

}
