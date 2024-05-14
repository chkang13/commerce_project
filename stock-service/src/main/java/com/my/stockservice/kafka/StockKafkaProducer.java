package com.my.stockservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.stockservice.kafka.dto.WriteOrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockKafkaProducer {

    private static final String WRITE_ORDER_TOPIC = "stock-topics2";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void updateOrder(final Long orderId) throws JsonProcessingException {
        final WriteOrderMessage writeStockMessage = new WriteOrderMessage(orderId);
        kafkaTemplate.send(WRITE_ORDER_TOPIC, objectMapper.writeValueAsString(writeStockMessage));

        log.info("주문 PAYBACK 요청보냄");
    }
}