package com.my.commerce.controller;

import com.my.commerce.service.OrderProductService;
import com.my.commerce.service.WishProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order-products")
public class OrderProductController {
    private final OrderProductService orderProductService;

}
