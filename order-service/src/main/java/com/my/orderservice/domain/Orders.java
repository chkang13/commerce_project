package com.my.orderservice.domain;

import com.my.coreservice.global.domain.BaseEntity;
import com.my.orderservice.util.OrderStatus;
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
@Table(name = "orders")
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long id;
    private int total;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Long memberId;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.REMOVE)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    public Orders(Long id, int total, OrderStatus orderStatus, Long memberId) {
        this.id = id;
        this.total = total;
        this.status = orderStatus;
        this.memberId = memberId;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

}
