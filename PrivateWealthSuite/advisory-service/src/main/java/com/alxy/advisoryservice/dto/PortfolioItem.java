package com.alxy.advisoryservice.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "portfolio_items")
@Data
public class PortfolioItem {

    @Id
    @Column(name = "item_id", length = 255, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false, referencedColumnName = "portfolio_id")
    private Portfolio portfolio;

    @Column(name = "product_code", length = 20, nullable = false)
    private String productCode;

    @Column(name = "product_name", length = 100, nullable = false)
    private String productName;

    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "amount", precision = 20, scale = 2)
    private BigDecimal amount;

    @Column(name = "unit_value", precision = 20, scale = 4)
    private BigDecimal unitValue;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
