package com.my.commerce.repository;

import com.my.commerce.domain.Wish;
import com.my.commerce.domain.WishProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishProductRepository extends JpaRepository<WishProduct, Long> {
}
