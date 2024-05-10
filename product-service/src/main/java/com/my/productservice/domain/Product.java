package com.my.productservice.domain;

import com.my.coreservice.global.domain.BaseEntity;
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
@Table(name = "product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long id;
    private String title;
    private String image;
    private int price;
    private int status;

    @Builder
    public Product(Long id, String title, String image, int price, int status) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.status = status;
    }

}
