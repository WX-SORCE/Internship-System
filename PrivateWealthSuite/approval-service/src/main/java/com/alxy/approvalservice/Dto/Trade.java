package com.alxy.approvalservice.Dto;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "trades")
public class Trade {
    @Id
    @Column(name = "trade_id")
    private String tradeId;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "product_code")
    private String productCode;

    @Column(columnDefinition = "ENUM('申购', '赎回', '调仓')")
    private String type;

    private BigDecimal amount;

    @Column(columnDefinition = "ENUM('待审批', '审批中', '已拒绝', '已执行')")
    private String status;

    private Date createTime;

    private Date updateTime;

    @PrePersist
    public void onCreate() {
        if (tradeId == null) {
            tradeId = UUID.randomUUID().toString().replace("-","").substring(12);
        }

    }
}