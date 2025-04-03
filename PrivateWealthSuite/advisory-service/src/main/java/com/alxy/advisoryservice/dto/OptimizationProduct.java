package com.alxy.advisoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptimizationProduct {
    private String productId;
    private double expectedReturn;
    private double risk;
}