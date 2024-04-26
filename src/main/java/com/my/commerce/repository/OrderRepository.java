package com.my.commerce.repository;

import com.my.commerce.domain.Orders;
import com.my.commerce.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByMemberId(Long memberId);

}
