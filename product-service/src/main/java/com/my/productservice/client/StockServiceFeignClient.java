package com.my.productservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "stock-service", url = "http://localhost:8083")
@CircuitBreaker(name = "addStock")
public interface StockServiceFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/stocks/{productId}/{count}", consumes = "application/json")
    String addStock(@PathVariable("productId") Long productId, @PathVariable("count") int count);
}
