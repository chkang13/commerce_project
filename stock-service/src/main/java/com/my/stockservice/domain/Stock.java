package com.my.stockservice.domain;

import com.my.coreservice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stock")
public class Stock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stockId")
    private Long id;
    private Long productId;
    private int stock;

    @Builder
    public Stock(Long id, Long productId, int stock) {
        this.id = id;
        this.productId = productId;
        this.stock = stock;
    }

    public void update(int stock) {
        this.stock = stock;
    }

}
