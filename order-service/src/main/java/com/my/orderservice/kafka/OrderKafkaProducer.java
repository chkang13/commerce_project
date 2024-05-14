package com.my.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.orderservice.kafka.dto.StockHandleDTO;
import com.my.orderservice.kafka.dto.StockHandleDTOS;
import com.my.orderservice.kafka.dto.WriteStockMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderKafkaProducer {

    private static final String WRITE_STOCK_TOPIC = "stock-topics1";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void reduceStock(final StockHandleDTOS stockHandleDTOS, final Long orderId) throws JsonProcessingException {
        final WriteStockMessage writeStockMessage = new WriteStockMessage(stockHandleDTOS, orderId);
        kafkaTemplate.send(WRITE_STOCK_TOPIC, objectMapper.writeValueAsString(writeStockMessage));

        log.info("재고 감소 카프카 요청 보냄");
    }
}