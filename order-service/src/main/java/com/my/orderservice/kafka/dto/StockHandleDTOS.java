package com.my.orderservice.kafka.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StockHandleDTOS {
    private List<StockHandleDTO> stockList;


    @Builder
    public StockHandleDTOS(List<StockHandleDTO> stockList) {
        this.stockList = stockList;
    }

    public static StockHandleDTOS toDTO(List<StockHandleDTO> stockList) {
        return StockHandleDTOS.builder()
                .stockList(stockList)
                .build();
    }
}
