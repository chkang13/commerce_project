package com.my.commerce.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id;
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String phone;
    private String address;

    @Builder
    public Member(Long id, String name, String email, String password, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public void update(String phone, String address) {
        this.phone = phone;
        this.address = address;
    }

    public void updatePassword(String password) {
        this.password=password;
    }
}