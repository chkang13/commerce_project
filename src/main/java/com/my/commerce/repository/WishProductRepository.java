package com.my.commerce.repository;

import com.my.commerce.domain.Wish;
import com.my.commerce.domain.WishProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishProductRepository extends JpaRepository<WishProduct, Long> {

}
