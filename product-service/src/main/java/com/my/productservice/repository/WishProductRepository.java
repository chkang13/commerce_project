package com.my.productservice.repository;

import com.my.productservice.domain.WishProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishProductRepository extends JpaRepository<WishProduct, Long> {

}
