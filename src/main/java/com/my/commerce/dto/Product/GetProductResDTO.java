package com.my.commerce.dto.Product;


import com.my.commerce.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetProductResDTO {
    private Long productId;
    private String title;
    private String image;
    private int price;

    @Builder
    public GetProductResDTO(Long productId, String title, String image, int price) {
        this.productId = productId;
        this.title = title;
        this.image = image;
        this.price = price;
    }

    public static GetProductResDTO toDTO(Product product) {
        return GetProductResDTO.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .image(product.getImage())
                .price(product.getPrice())
                .build();
    }
}
