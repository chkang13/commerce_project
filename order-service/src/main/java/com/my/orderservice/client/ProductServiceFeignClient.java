package com.my.orderservice.client;

import com.my.orderservice.dto.client.GetProductResDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "product-service", url = "http://localhost:8081")
@CircuitBreaker(name = "getProduct")
public interface ProductServiceFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/products/internal/{productId}", consumes = "application/json")
    GetProductResDTO getInProduct(@PathVariable("productId") Long productId);
}
