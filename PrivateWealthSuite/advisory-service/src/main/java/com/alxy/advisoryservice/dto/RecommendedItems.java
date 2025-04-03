package com.alxy.advisoryservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecommendedItems {

    private String productName;

    private BigDecimal productPercent;
}