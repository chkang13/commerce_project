package com.my.commerce.repository;

import com.my.commerce.domain.OrderProduct;
import com.my.commerce.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
