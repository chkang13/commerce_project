package com.my.productservice.dto.Order;

import com.my.productservice.domain.Member;
import com.my.productservice.domain.Orders;
import com.my.productservice.util.OrderStatus;
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
