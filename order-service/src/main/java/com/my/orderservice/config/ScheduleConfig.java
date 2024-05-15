package com.my.orderservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.coreservice.global.common.BasicException;
import com.my.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleConfig {
    private final OrderService orderService;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행 (초, 분, 시, 일, 월, 요일)
    public void run1() throws BasicException, JsonProcessingException {
        // 배송 상태 변경
        orderService.updateStatus();

        // 반품 처리
        orderService.updateRefund();
    }

    @Scheduled(fixedRate = 300000) // 5 분마다 실행
    public void run2() throws BasicException, JsonProcessingException {
        // 결제 상태 변경
        orderService.updatePayment();
    }
}