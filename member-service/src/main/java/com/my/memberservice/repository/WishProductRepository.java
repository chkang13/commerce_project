package com.my.memberservice.repository;

import com.my.memberservice.domain.WishProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishProductRepository extends JpaRepository<WishProduct, Long> {

}
