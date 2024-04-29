package com.my.orderservice.repository;

import com.my.orderservice.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByMemberId(Long memberId);

}
