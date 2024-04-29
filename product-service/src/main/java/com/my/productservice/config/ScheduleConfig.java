package com.my.productservice.config;

import com.my.coreservice.global.common.BasicException;
import com.my.productservice.service.OrderService;
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
    public void run() throws BasicException {
        // 배송 상태 변경
        orderService.updateStatus();

        // 반품 처리
        orderService.updateRefund();
    }
}