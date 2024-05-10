package com.my.stockservice.kafka.dto;

import java.util.List;

public record WriteStockMessage(List<StockHandleDTO> stockHandleDTOS, Long orderId) {
}
