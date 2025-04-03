package com.alxy.clientservice.entiy;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @Column(name = "client_id")
    private String clientId;

    private String name;

    @Column(length = 1)
    private String gender;

    private LocalDate birthday;

    @Column(unique = true)
    private String phoneNumber;

    private String email;

    private String nationality;

    @Column(name = "id_type")
    private String idType;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "income_level")
    private Integer incomeLevel;

    @Column(name = "risk_level")
    private Integer riskLevel;

    @Column(name = "total_assets")
    private BigDecimal totalAssets;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "kyc_due_date")
    private LocalDate kycDueDate;

    @Column(name = "status")
    private String status;

    private String remarks;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    private String userId;

    private String advisorId;

    @PrePersist
    public void onCreate() {
        if (status == null) {
            status = "正常";
        }
        if (clientId == null) {
            clientId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
        }
        registerDate = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}