package com.my.commerce.controller;

import com.my.commerce.service.ProductService;
import com.my.commerce.service.WishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wishes")
public class WishController {
    private final WishService wishService;

}