package com.my.stockservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.stockservice.kafka.dto.StockHandleDTOS;
import com.my.stockservice.kafka.dto.WriteStockMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockFacade {

    private final StockService stockService;
    private final RedissonClient redissonClient;

    /**
     * 재고 감소 kafka 통신
     */
    public void reduceStock(final WriteStockMessage writeStockMessage) throws JsonProcessingException {
        RLock lock = redissonClient.getLock(String.format("order:%d", writeStockMessage.orderId()));

        try {
            boolean available = lock.tryLock(5, 3, TimeUnit.SECONDS);
            if (!available) {
                System.out.println("redisson getLock timeout");
                return;
            }

            stockService.reduceStock(writeStockMessage);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
         }

    }

    // 테스트용
    public void reduceStock2(final Long productId, final int count) {
        RLock lock = redissonClient.getLock(String.format("product:%d", productId));
        try {
            boolean available = lock.tryLock(5, 3, TimeUnit.SECONDS);
            if (!available) {
                System.out.println("redisson getLock timeout");
                return;
            }

            stockService.reduceStock2(productId,count);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

    // 테스트용
    /**
     * 재고 감소 api
     */
    public String decreaseStock(StockHandleDTOS stockHandleDTOS) throws JsonProcessingException {
        RLock lock = redissonClient.getLock(String.format("product"));
        try {
            boolean available = lock.tryLock(5, 3, TimeUnit.SECONDS);
            if (!available) {
                System.out.println("redisson getLock timeout");
                return "q";
            }

            stockService.decreaseStock(stockHandleDTOS);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
            return  "o";
        }

    }

}


