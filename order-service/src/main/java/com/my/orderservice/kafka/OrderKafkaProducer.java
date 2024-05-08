package com.my.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.orderservice.kafka.dto.StockHandleDTO;
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

    private static final String WRITE_BOARD_TOPIC = "write-order";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void reduceStock(final List<StockHandleDTO> stockHandleDTOS, final Long orderId) throws JsonProcessingException {
        final WriteStockMessage writeStockMessage = new WriteStockMessage(stockHandleDTOS, orderId);
        kafkaTemplate.send(WRITE_BOARD_TOPIC, objectMapper.writeValueAsString(writeStockMessage));
        log.info(String.valueOf(stockHandleDTOS.get(0).getProductId()));
    }
}