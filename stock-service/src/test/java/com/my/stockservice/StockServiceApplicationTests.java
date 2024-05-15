package com.my.stockservice;

import com.my.stockservice.domain.Stock;
import com.my.stockservice.repository.StockRepository;
import com.my.stockservice.service.StockFacade;
import com.my.stockservice.service.StockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class StockServiceApplicationTests {
    private final StockRepository stockRepository;

    private final StockFacade stockFacade;

    @Autowired
    public StockServiceApplicationTests(final StockRepository stockRepository, final StockFacade stockFacade) {
        this.stockRepository = stockRepository;
        this.stockFacade = stockFacade;
    }

    @Test
    @DisplayName("테스트 성공")
    public void reduceStock() throws InterruptedException {
        Long stockId = stockRepository.save(new Stock(1L, 1L, 100)).getId();

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    stockFacade.reduceStock3(1L, 1);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        Stock actual = stockRepository.findById(stockId)
                .orElseThrow();

        assertThat(actual.getStock()).isZero();
    }

}
