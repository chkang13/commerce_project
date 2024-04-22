package com.my.commerce.service;

import com.my.commerce.repository.OrderProductRepository;
import com.my.commerce.repository.WishProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

}
