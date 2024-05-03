package com.my.memberservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "order-service", url = "http://localhost:8082")
@CircuitBreaker(name = "addWish")
public interface OrderServiceFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/wishes/{memberId}", consumes = "application/json")
    String addWish(@PathVariable("memberId") Long memberId);
}
