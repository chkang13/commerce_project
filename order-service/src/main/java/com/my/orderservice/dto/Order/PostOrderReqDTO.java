package com.my.orderservice.dto.Order;

import com.my.orderservice.domain.Orders;
import com.my.orderservice.util.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostOrderReqDTO {
    int total;
    private List<PostOrderProductReqDTO> orderProducts;

    @Builder
    public PostOrderReqDTO(int total, List<PostOrderProductReqDTO> postOrderProductReqDTOS) {
        this.total = total;
        this.orderProducts = postOrderProductReqDTOS;
    }

    public static Orders toEntity(Long memberId, int total, OrderStatus orderStatus) {
        return Orders.builder()
                .memberId(memberId)
                .orderStatus(orderStatus)
                .total(total)
                .build();
    }
}
