package com.my.stockservice.kafka.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StockHandleDTOS {
    private List<StockHandleDTO> stockList;
}
