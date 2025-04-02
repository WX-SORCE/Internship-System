package com.alxy.portfolioservice.entiy;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "productvaluehistory")
public class ProductValueHistory {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "item_id", length = 255, nullable = false)
    private String itemId;

    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "value")
    private double value;

    @Column(name = "return_rate")
    private BigDecimal returnRate;

    @Column(name = "volatility")
    private BigDecimal volatility;

    @Column(name = "market_index_value")
    private BigDecimal marketIndexValue;

    @Column(name = "volume")
    private Long volume;

    @Column(name = "source")
    private String source;

    public ProductValueHistory(double value, Date date, String itemId) {
        this.value = value;
        this.date = date;
        this.itemId = itemId;
    }
}