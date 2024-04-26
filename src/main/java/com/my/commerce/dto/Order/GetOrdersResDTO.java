package com.my.commerce.dto.Order;


import com.my.commerce.domain.Orders;
import com.my.commerce.util.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetOrdersResDTO {
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private int total;

    @Builder
    public GetOrdersResDTO(Long orderId, OrderStatus orderStatus, int total) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.total = total;
    }

    public static GetOrdersResDTO toDTO(Orders orders) {
        return GetOrdersResDTO.builder()
                .orderId(orders.getId())
                .orderStatus(orders.getStatus())
                .total(orders.getTotal())
                .build();
    }
}
