package com.alxy.advisoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "recommended_items")
public class RecommendedItem {
    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "recommendation_id")
    private String recommendationId;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "product_percent")
    private BigDecimal productPercent;
}