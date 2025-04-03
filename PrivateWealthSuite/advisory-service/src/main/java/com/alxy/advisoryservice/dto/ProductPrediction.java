package com.alxy.advisoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProductPrediction {
    private String productId;
    private String productType;
    private double expectedReturn;
    private double risk;
    private Date predictionDate;
}