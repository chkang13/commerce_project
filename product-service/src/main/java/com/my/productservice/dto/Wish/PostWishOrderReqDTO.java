package com.my.productservice.dto.Wish;

import com.my.productservice.domain.Member;
import com.my.productservice.domain.Orders;
import com.my.productservice.util.OrderStatus;
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
