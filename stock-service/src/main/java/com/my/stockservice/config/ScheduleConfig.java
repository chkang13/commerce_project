package com.my.stockservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.coreservice.global.common.BasicException;
import com.my.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleConfig {
    private final StockService stockService;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행 (초, 분, 시, 일, 월, 요일)
    public void run() throws BasicException, JsonProcessingException {
        // 재고 DB에 반영
        stockService.updateData();
    }

}