package com.my.commerce.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import java.sql.Timestamp;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp updatedAt;
}
