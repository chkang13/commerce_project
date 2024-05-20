package com.my.stockservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.coreservice.global.common.BaseResponse;
import com.my.stockservice.kafka.dto.StockHandleDTOS;
import com.my.stockservice.service.StockFacade;
import com.my.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;
    private final StockFacade stockFacade;

    /**
     * 재고 추가 API
     *
     * @return String
     */
    @PostMapping("/{productId}/{count}")
    public BaseResponse<String> addStock(@PathVariable("productId") Long productId, @PathVariable("count") int count) {
        String addStockRes = stockService.addStock(productId, count);

        return new BaseResponse<>(addStockRes);
    }

    /**
     * 재고 감소 API (사용은 안함)
     *
     * @return String
     */
    @PostMapping("/down")
    public BaseResponse<String> decreaseStock(@RequestBody StockHandleDTOS stockHandleDTOS) throws JsonProcessingException {
        String decreaseStock = stockFacade.decreaseStock(stockHandleDTOS);

        return new BaseResponse<>(decreaseStock);
    }

    /**
     * 재고 증가 API
     *
     * @return String
     */
    @PostMapping("/up")
    public BaseResponse<String> increaseStock(@RequestBody StockHandleDTOS stockHandleDTOS) {
        String increaseStock = stockService.increaseStock(stockHandleDTOS);

        return new BaseResponse<>(increaseStock);
    }


}
