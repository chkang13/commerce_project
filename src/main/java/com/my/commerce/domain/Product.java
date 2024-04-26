package com.my.commerce.domain;

import com.my.commerce.util.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
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
    private int stock;
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<OrderProduct> orderProducts = new ArrayList<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<WishProduct> wishProducts = new ArrayList<>();

    @Builder
    public Product(Long id, String title, String image, int price, int status, int stock) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.status = status;
        this.stock = stock;
    }

    public void updateStock(int stock) {
        this.stock = stock;
    }

}
