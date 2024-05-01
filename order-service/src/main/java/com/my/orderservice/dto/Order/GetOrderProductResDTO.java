package com.my.orderservice.dto.Order;


import com.my.orderservice.domain.OrderProduct;
import com.my.orderservice.dto.client.GetProductResDTO;
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

    public static GetOrderProductResDTO toDTO(OrderProduct orderProduct, GetProductResDTO getProductResDTO) {
        return GetOrderProductResDTO.builder()
                .orderProductId(orderProduct.getId())
                .productId(getProductResDTO.getProductId())
                .title(getProductResDTO.getTitle())
                .image(getProductResDTO.getImage())
                .count(orderProduct.getCount())
                .price(orderProduct.getPrice())
                .build();
    }
}
