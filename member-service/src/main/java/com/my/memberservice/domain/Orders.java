package com.my.memberservice.domain;

import com.my.coreservice.global.domain.BaseEntity;
import jakarta.persistence.*;

import com.my.memberservice.util.OrderStatus;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.REMOVE)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    public Orders(Long id, int total, OrderStatus orderStatus, Member member) {
        this.id = id;
        this.total = total;
        this.status = orderStatus;
        this.member = member;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

}
