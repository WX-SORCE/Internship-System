package com.alxy.portfolioservice.entiy;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "portfolio_items")
@Data
public class PortfolioItems {

    @Id
    @Column(name = "item_id", length = 255)
    private String itemId;

    @Column(name = "portfolio_id", nullable = false)
    private String portfoliosId;

    @Column(name = "product_code", length = 20, nullable = false)
    private String productCode;

    @Column(name = "product_name", length = 100, nullable = false)
    private String productName;

    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "unit_value", precision = 20, scale = 4)
    private BigDecimal unitValue;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Override
    public String toString() {
        return "PortfolioItems{" +
                "itemId='" + itemId + '\'' +
                ", portfoliosId='" + portfoliosId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", unitValue=" + unitValue +
                ", createdAt=" + createdAt +
                '}';
    }
}