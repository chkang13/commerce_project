package com.my.commerce.repository;

import com.my.commerce.domain.Product;
import com.my.commerce.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
}
