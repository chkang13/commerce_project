package com.my.stockservice.kafka.dto;

import java.util.List;

public record WriteStockMessage(StockHandleDTOS stockHandleDTOS, Long orderId) {
}
