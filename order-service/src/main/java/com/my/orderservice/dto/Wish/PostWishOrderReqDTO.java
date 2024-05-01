package com.my.orderservice.dto.Wish;

import com.my.orderservice.domain.Orders;
import com.my.orderservice.util.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostWishOrderReqDTO {
    int total;
    private List<PostOrderWIshProductReqDTO> orderProducts;

    @Builder
    public PostWishOrderReqDTO(int total, List<PostOrderWIshProductReqDTO> postOrderProductReqDTOS) {
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
