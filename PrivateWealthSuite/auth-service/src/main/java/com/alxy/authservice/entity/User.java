package com.alxy.authservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String name;

    private String email;

    private String oauth2Provider;          // Google、Wechat

    private String oauth2Id;

    @Column(nullable = false)
    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Integer identityLevel;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = "正常";
        }
        if(identityLevel == null){
            identityLevel = 0 ;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}