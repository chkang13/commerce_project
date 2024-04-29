package com.my.productservice.dto.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PatchMemberReqDTO {
    private String phone;
    private String address;

    @Builder
    public PatchMemberReqDTO(String phone, String address) {
        this.phone = phone;
        this.address = address;
    }
}
