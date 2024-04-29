package com.my.memberservice.domain;

import com.my.coreservice.global.domain.BaseEntity;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_product")
public class OrderProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderProductId")
    private Long id;
    private int count;
    private int price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Orders orders;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Builder
    public OrderProduct(Long id, int count, int price, Orders orders, Product product) {
        this.id = id;
        this.count = count;
        this.price = price;
        this.orders = orders;
        this.product = product;
    }
}
