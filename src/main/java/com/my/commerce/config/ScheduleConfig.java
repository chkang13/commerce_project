package com.my.commerce.config;

import com.my.commerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleConfig {
    private final OrderService orderService;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행 (초, 분, 시, 일, 월, 요일)
    public void run() {
        orderService.updateStatus();
    }
}