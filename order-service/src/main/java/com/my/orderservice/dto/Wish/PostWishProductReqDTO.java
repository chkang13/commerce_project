package com.my.orderservice.dto.Wish;

import com.my.orderservice.domain.Wish;
import com.my.orderservice.domain.WishProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostWishProductReqDTO {
    private Long productId;
    private int count;

    @Builder
    public PostWishProductReqDTO(Long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public static WishProduct toEntity(Wish wish, PostWishProductReqDTO postWishProductReqDTO) {
        return WishProduct.builder()
                .count(postWishProductReqDTO.getCount())
                .wish(wish)
                .productId(postWishProductReqDTO.getProductId())
                .build();
    }

}
