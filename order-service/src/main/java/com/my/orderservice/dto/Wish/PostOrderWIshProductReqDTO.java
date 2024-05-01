package com.my.orderservice.dto.Wish;

import com.my.orderservice.domain.OrderProduct;
import com.my.orderservice.domain.Orders;
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

    public static OrderProduct toEntity(Orders orders, PostOrderWIshProductReqDTO postOrderWIshProductReqDTO) {
        return OrderProduct.builder()
                .count(postOrderWIshProductReqDTO.getCount())
                .price(postOrderWIshProductReqDTO.getPrice())
                .orders(orders)
                .productId(postOrderWIshProductReqDTO.getProductId())
                .build();
    }

}
