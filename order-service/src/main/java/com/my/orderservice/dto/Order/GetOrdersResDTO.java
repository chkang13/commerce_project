package com.my.orderservice.dto.Order;


import com.my.orderservice.domain.Orders;
import com.my.orderservice.util.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

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
