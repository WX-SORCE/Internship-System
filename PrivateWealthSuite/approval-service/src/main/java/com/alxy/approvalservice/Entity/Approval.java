package com.alxy.approvalservice.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "approval_records")
public class Approval {

    @Id
    @Column(name = "approval_id")
    private String approvalId;

    @Column(name = "trade_id")
    private String tradeId;

    @Column(name = "level", nullable = false)
    private Integer level;
    // 2客户经理审核  3风控人员审核  4合规人员 5通过

    @Column(name = "approver_id")
    private String approverId;
    // User userId

    @Column(name = "decision")
    private String decision;
    // 通过、拒绝、未处理

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        if (approvalId == null) {
            approvalId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
        }
        if (level == null) {
            level = 2;
        }
        if (decision == null){
            decision = "未处理";
        }
        createdAt = LocalDateTime.now();
    }
}