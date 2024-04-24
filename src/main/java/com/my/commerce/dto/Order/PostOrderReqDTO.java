package com.my.commerce.dto.Order;

import com.my.commerce.domain.Member;
import com.my.commerce.domain.Orders;
import com.my.commerce.util.OrderStatus;
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

    public static Orders toEntity(Member member, int total, OrderStatus orderStatus) {
        return Orders.builder()
                .member(member)
                .orderStatus(orderStatus)
                .total(total)
                .build();
    }
}
