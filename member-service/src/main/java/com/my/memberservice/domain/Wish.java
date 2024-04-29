package com.my.memberservice.domain;

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
    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;
    @OneToMany(mappedBy = "wish", cascade = CascadeType.REMOVE)
    private List<WishProduct> wishProducts = new ArrayList<>();

    @Builder
    public Wish(Long id, Member member) {
        this.id = id;
        this.member = member;
    }
}
