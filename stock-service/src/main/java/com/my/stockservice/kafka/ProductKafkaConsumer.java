package com.my.stockservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.stockservice.kafka.dto.WriteStockMessage;
import com.my.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductKafkaConsumer {

    private final StockService stockService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "test-topic2", groupId = "product-transaction-result-group")
    public void consumeBoardWriteEvent(final String writeStockMessage) throws JsonProcessingException {
        final WriteStockMessage message = objectMapper.readValue(writeStockMessage, WriteStockMessage.class);
        log.info(writeStockMessage);

       stockService.reduceStock2(message);
    }
}
