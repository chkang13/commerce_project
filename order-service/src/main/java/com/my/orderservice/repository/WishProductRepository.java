package com.my.orderservice.repository;

import com.my.orderservice.domain.WishProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishProductRepository extends JpaRepository<WishProduct, Long> {

}
