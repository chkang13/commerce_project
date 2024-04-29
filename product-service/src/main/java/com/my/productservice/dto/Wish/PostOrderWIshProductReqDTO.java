package com.my.productservice.dto.Wish;

import com.my.productservice.domain.OrderProduct;
import com.my.productservice.domain.Orders;
import com.my.productservice.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostOrderWIshProductReqDTO {
    private Long productId;
    private Long wishProductId;
    private int count;
    private int price;

    @Builder
    public PostOrderWIshProductReqDTO(Long productId, Long wishProductId, int count, int price) {
        this.productId = productId;
        this.wishProductId = wishProductId;
        this.count = count;
        this.price = price;
    }

    public static OrderProduct toEntity(Orders orders, Product product, PostOrderWIshProductReqDTO postOrderWIshProductReqDTO) {
        return OrderProduct.builder()
                .count(postOrderWIshProductReqDTO.getCount())
                .price(postOrderWIshProductReqDTO.getPrice())
                .orders(orders)
                .product(product)
                .build();
    }

}
