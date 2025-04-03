package com.alxy.advisoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PortfolioItemAllocation {
    private String productCode;
    private String productName;
    private double allocationPercentage;
    private BigDecimal recommendedAmount;
    private String productType;
}