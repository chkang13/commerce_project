package com.my.orderservice.kafka.dto;

import java.util.List;

public record WriteStockMessage(StockHandleDTOS stockHandleDTOS, Long orderId) {
}
