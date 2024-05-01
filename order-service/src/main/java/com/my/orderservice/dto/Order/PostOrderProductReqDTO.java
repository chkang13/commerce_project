package com.my.orderservice.dto.Order;

import com.my.orderservice.domain.OrderProduct;
import com.my.orderservice.domain.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostOrderProductReqDTO {
    private Long productId;
    private int count;
    private int price;

    @Builder
    public PostOrderProductReqDTO(Long productId, int count, int price) {
        this.productId = productId;
        this.count = count;
        this.price = price;
    }

    public static OrderProduct toEntity(Orders orders, PostOrderProductReqDTO postOrderProductReqDTO) {
        return OrderProduct.builder()
                .count(postOrderProductReqDTO.getCount())
                .price(postOrderProductReqDTO.getPrice())
                .orders(orders)
                .productId(postOrderProductReqDTO.getProductId())
                .build();
    }

}
