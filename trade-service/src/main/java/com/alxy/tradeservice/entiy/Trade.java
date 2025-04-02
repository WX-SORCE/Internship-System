package com.alxy.tradeservice.entiy;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @PrePersist
    public void onCreate() {
        if (tradeId == null) {
            tradeId = UUID.randomUUID().toString().replaceAll("-","").substring(0,12);
        }
        if(type==null){
            type = "申购";
        }
        if(status == null){
            status = "待审批";
        }
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }

}