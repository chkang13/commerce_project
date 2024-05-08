package com.my.orderservice.kafka.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StockHandleDTO {
    private Long productId;
    private int count;


    @Builder
    public StockHandleDTO(Long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public static StockHandleDTO toDTO(Long productId, int count) {
        return StockHandleDTO.builder()
                .productId(productId)
                .count(count)
                .build();
    }
}
