package com.my.commerce.dto;

import com.my.commerce.domain.Member;
import com.my.commerce.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostProductReqDTO {
    private String title;
    private String image;
    private int price;
    private int stock;

    @Builder
    public PostProductReqDTO(String title, String image, int price, int stock) {
        this.title = title;
        this.image = image;
        this.price = price;
        this.stock = stock;
    }

    public static Product toEntity(PostProductReqDTO postProductReqDTO, int status) {
        return Product.builder()
                .title(postProductReqDTO.getTitle())
                .image(postProductReqDTO.getImage())
                .price(postProductReqDTO.getPrice())
                .status(status)
                .stock(postProductReqDTO.getStock())
                .build();
    }

}
