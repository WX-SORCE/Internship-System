package com.alxy.advisoryservice.dto;


import lombok.Data;

import java.util.List;


@Data
public class Recommendations {


    private String recommendationId;
    private String recommendationName;
    private Integer riskLevel;
    private Integer yieldRate;
    private List<RecommendedItems> recommendedItems;



}