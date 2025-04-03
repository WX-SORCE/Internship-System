package com.alxy.advisoryservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OptimalPortfolio {
    private List<PortfolioItemAllocation> items;
    private double expectedReturn;
    private double riskScore;
    private int riskLevel;
    private Date calculationTime;
    
    public OptimalPortfolio() {
        this.items = new ArrayList<>();
        this.calculationTime = new Date();
    }
    
    public void addItem(PortfolioItemAllocation item) {
        this.items.add(item);
    }
}