package com.my.memberservice.dto.Wish;

import com.my.memberservice.domain.Member;
import com.my.memberservice.domain.Orders;
import com.my.memberservice.util.OrderStatus;
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

    public static Orders toEntity(Member member, int total, OrderStatus orderStatus) {
        return Orders.builder()
                .member(member)
                .orderStatus(orderStatus)
                .total(total)
                .build();
    }
}
