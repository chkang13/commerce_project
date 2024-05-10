package com.my.orderservice.client;

import com.my.orderservice.kafka.dto.StockHandleDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "stock-service", url = "http://localhost:8083")
@CircuitBreaker(name = "updateStock")
public interface StockServiceFeignClient {
    @RequestMapping(method = RequestMethod.PATCH, value = "/stocks/down", consumes = "application/json")
    String reduceStock(@RequestBody List<StockHandleDTO> stockHandleDTOS);

    @RequestMapping(method = RequestMethod.PATCH, value = "/stocks/up", consumes = "application/json")
    String increaseStock(@RequestBody List<StockHandleDTO> stockHandleDTOS);
}
