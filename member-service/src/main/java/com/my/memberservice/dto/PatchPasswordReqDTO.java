package com.my.memberservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PatchPasswordReqDTO {
    private String currentPassword;
    private String changedPassword;
    @Builder
    public PatchPasswordReqDTO(String currentPassword, String changedPassword) {
        this.currentPassword = currentPassword;
        this.changedPassword = changedPassword;

    }
}
