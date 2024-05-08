package com.my.productservice.kafka.dto;

import java.util.List;

public record WriteStockMessage(List<StockHandleDTO> stockHandleDTOS, Long orderId) {
}
