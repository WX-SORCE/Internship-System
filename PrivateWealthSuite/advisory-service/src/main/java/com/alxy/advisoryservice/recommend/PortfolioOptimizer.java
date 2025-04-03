package com.alxy.advisoryservice.recommend;

import com.alxy.advisoryservice.dto.OptimalPortfolio;
import com.alxy.advisoryservice.dto.ProductStats;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PortfolioOptimizer {
    /**
     * 根据产品统计信息和风险等级优化投资组合
     */
    OptimalPortfolio optimize(List<ProductStats> productStats, int riskLevel);
}