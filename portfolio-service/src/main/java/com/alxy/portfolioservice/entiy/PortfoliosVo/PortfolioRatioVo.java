package com.alxy.portfolioservice.entiy.PortfoliosVo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PortfolioRatioVo {
    String name;
    private BigDecimal value;
}