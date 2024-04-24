package com.my.commerce.dto.Order;

import com.my.commerce.domain.Member;
import com.my.commerce.domain.OrderProduct;
import com.my.commerce.domain.Orders;
import com.my.commerce.domain.Product;
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

    public static OrderProduct toEntity(Orders orders, Product product, PostOrderProductReqDTO postOrderProductReqDTO) {
        return OrderProduct.builder()
                .count(postOrderProductReqDTO.getCount())
                .price(postOrderProductReqDTO.getPrice())
                .orders(orders)
                .product(product)
                .build();
    }

}
