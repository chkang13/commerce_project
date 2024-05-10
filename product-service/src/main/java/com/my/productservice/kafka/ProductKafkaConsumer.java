package com.my.productservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.productservice.kafka.dto.WriteStockMessage;
import com.my.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductKafkaConsumer {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "test-topic", groupId = "product-transaction-result-group")
    public void consumeBoardWriteEvent(final String writeStockMessage) throws JsonProcessingException {
        final WriteStockMessage message = objectMapper.readValue(writeStockMessage, WriteStockMessage.class);
        log.info(writeStockMessage);

       // productService.reduceStock(message);
    }
}
