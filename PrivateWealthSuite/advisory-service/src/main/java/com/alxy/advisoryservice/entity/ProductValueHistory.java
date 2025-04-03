package com.alxy.advisoryservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productvaluehistory")
@Data
public class ProductValueHistory {

    @Id
    @Column(name = "id", length = 255, nullable = false)
    private String id;

    @Column(name = "item_id", length = 255, nullable = false)
    private String itemId;

    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "value", precision = 20, scale = 4, nullable = false)
    private BigDecimal value;

    @Column(name = "return_rate", precision = 10, scale = 4)
    private BigDecimal returnRate;

    @Column(name = "volatility", precision = 10, scale = 4)
    private BigDecimal volatility;

    @Column(name = "market_index_value", precision = 20, scale = 4)
    private BigDecimal marketIndexValue;

    @Column(name = "volume")
    private Long volume;

    @Column(name = "source", length = 50)
    private String source;
}