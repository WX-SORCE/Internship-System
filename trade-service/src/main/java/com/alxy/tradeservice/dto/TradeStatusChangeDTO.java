package com.alxy.tradeservice.dto;

import lombok.Data;

import java.util.Date;

/**
 * 交易状态变更数据传输对象
 */
@Data
public class TradeStatusChangeDTO {
    private String tradeId; // 交易ID
    private String clientId; // 客户ID
    private String oldStatus; // 旧状态
    private String newStatus; // 新状态
    private Date changeTime; // 变更时间
    private String remark; // 备注信息
}