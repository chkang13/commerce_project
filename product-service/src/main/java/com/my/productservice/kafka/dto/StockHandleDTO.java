package com.my.productservice.kafka.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StockHandleDTO {
    private Long productId;
    private int count;
}
