package com.my.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.orderservice.kafka.dto.WriteOrderMessage;
import com.my.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderKafkaConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "stock-topics2", groupId = "order-transaction-result-group")
    public void updateOrder(final String writeOrderMessage) throws JsonProcessingException {
        final WriteOrderMessage message = objectMapper.readValue(writeOrderMessage, WriteOrderMessage.class);
        log.info("주문 PAYBACK 요청 받음");

       orderService.deletePayment(message);
    }

}
