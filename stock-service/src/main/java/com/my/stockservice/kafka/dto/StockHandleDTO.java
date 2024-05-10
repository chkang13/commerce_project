package com.my.stockservice.kafka.dto;

import lombok.Getter;

@Getter
public class StockHandleDTO {
    private Long productId;
    private int count;
}
