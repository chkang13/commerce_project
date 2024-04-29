package com.my.productservice.dto.Wish;

import com.my.productservice.domain.Product;
import com.my.productservice.domain.Wish;
import com.my.productservice.domain.WishProduct;
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

    public static WishProduct toEntity(Wish wish, Product product, int count) {
        return WishProduct.builder()
                .count(count)
                .wish(wish)
                .product(product)
                .build();
    }

}
