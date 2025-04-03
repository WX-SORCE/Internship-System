package com.alxy.clientservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateInfo {
    String clientId;
    BigDecimal total_assets;
    Integer riskLevel;
    Integer incomeLevel;
    LocalDate kycDueDate;
}
