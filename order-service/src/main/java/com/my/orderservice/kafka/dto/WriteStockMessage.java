package com.my.orderservice.kafka.dto;

import java.util.List;

public record WriteStockMessage(List<StockHandleDTO> stockHandleDTOS, Long orderId) {
}
