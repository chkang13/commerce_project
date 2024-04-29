package com.my.productservice.dto.Order;


import com.my.productservice.domain.Orders;
import com.my.productservice.util.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetOrderResDTO {
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private int total;
    private List<GetOrderProductResDTO> orderProducts;

    @Builder
    public GetOrderResDTO(Long orderId, OrderStatus orderStatus, int total, List<GetOrderProductResDTO> orderProducts) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.total = total;
        this.orderProducts = orderProducts;
    }

    public static GetOrderResDTO toDTO(Orders orders, List<GetOrderProductResDTO> orderProducts) {
        return GetOrderResDTO.builder()
                .orderId(orders.getId())
                .orderStatus(orders.getStatus())
                .total(orders.getTotal())
                .orderProducts(orderProducts)
                .build();
    }
}
