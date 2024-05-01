package com.my.orderservice.domain;

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
@Table(name = "wish")
public class Wish extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishId")
    private Long id;
    private Long memberId;
    @OneToMany(mappedBy = "wish", cascade = CascadeType.REMOVE)
    private List<WishProduct> wishProducts = new ArrayList<>();

    @Builder
    public Wish(Long id, Long memberId) {
        this.id = id;
        this.memberId = memberId;
    }
}
