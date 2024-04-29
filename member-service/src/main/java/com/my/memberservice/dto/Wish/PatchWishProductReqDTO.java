package com.my.memberservice.dto.Wish;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PatchWishProductReqDTO {
    private Long wishProductId;
    private int count;

    @Builder
    public PatchWishProductReqDTO(Long wishProductId, int count) {
        this.wishProductId = wishProductId;
        this.count = count;
    }
}
