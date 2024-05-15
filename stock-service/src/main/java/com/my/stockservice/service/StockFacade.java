package com.my.stockservice.service;

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



    public void reduceStock3(final Long productId, final int count) {
        RLock lock = redissonClient.getLock(String.format("product:%d", productId));
        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
            if (!available) {
                System.out.println("redisson getLock timeout");
                return;
            }

            stockService.reduceStock4(productId,count);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

}


