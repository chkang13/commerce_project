package com.my.memberservice.dto.Order;

import com.my.memberservice.domain.Member;
import com.my.memberservice.domain.Orders;
import com.my.memberservice.util.OrderStatus;
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
