package com.alxy.advisoryservice.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "portfolios")
@Data
public class Portfolio {

    @Id
    @Column(name = "portfolio_id", length = 255, nullable = false)
    private String id;

    @Column(name = "client_id", length = 255)
    private String clientId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "total_value", precision = 20, scale = 2)
    private BigDecimal totalValue;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioItem> portfolioItems;
}