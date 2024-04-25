package com.my.commerce.dto.Order;


import com.my.commerce.domain.OrderProduct;
import com.my.commerce.domain.Product;
import com.my.commerce.util.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetOrderProductResDTO {
    private Long orderProductId;
    private Long productId;
    private String title;
    private String image;
    private int count;
    private int price;

    @Builder
    public GetOrderProductResDTO(Long orderProductId, Long productId, String title, String image, int count, int price) {
        this.orderProductId = orderProductId;
        this.productId = productId;
        this.title = title;
        this.image = image;
        this.count = count;
        this.price = price;
    }

    public static GetOrderProductResDTO toDTO(OrderProduct orderProduct, Product product) {
        return GetOrderProductResDTO.builder()
                .orderProductId(orderProduct.getId())
                .productId(product.getId())
                .title(product.getTitle())
                .image(product.getImage())
                .count(orderProduct.getCount())
                .price(orderProduct.getPrice())
                .build();
    }
}
