package com.my.commerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wish_product")
public class WishProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishProductId")
    private Long id;
    private int count;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishId")
    private Wish wish;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Builder
    public WishProduct(Long id, int count, Wish wish, Product product) {
        this.id = id;
        this.count = count;
        this.wish = wish;
        this.product = product;
    }

}
