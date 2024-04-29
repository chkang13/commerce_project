package com.my.orderservice.dto.Wish;


import com.my.orderservice.domain.Product;
import com.my.orderservice.domain.WishProduct;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetWishProductResDTO {
    private Long productId;
    private Long wishProductId;
    private String title;
    private String image;
    private int count;
    private int price;

    @Builder
    public GetWishProductResDTO(Long productId, Long wishProductId, String title, String image, int count, int price) {
        this.productId = productId;
        this.wishProductId = wishProductId;
        this.title = title;
        this.image = image;
        this.count = count;
        this.price = price;
    }

    public static GetWishProductResDTO toDTO(Product product, WishProduct wishProduct, int price) {
        return GetWishProductResDTO.builder()
                .productId(product.getId())
                .wishProductId(wishProduct.getId())
                .title(product.getTitle())
                .image(product.getImage())
                .count(wishProduct.getCount())
                .price(price)
                .build();
    }
}
