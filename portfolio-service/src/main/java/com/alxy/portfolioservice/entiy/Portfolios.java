package com.alxy.portfolioservice.entiy;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "portfolios")
@Data
public class Portfolios {

    @Id
    @Column(name = "portfolio_id", length = 255)
    private String portfolioId;

    @Column(name = "client_id", length = 255)
    private String clientId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "total_value", precision = 20, scale = 2)
    private BigDecimal totalValue;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(mappedBy = "portfoliosId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PortfolioItems> portfolioItems;

    @Override
    public String toString() {
        return "Portfolios{" +
                "portfolioId='" + portfolioId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", name='" + name + '\'' +
                ", totalValue=" + totalValue +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", portfolioItemsCount=" + (portfolioItems != null ? portfolioItems.size() : 0) +
                '}';
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
        if (portfolioId == null) {
            portfolioId = UUID.randomUUID().toString().replaceAll("-","").substring(12);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}