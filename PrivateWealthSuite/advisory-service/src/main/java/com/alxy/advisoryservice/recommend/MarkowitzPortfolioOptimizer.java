package com.alxy.advisoryservice.recommend;

import com.alxy.advisoryservice.dto.OptimalPortfolio;
import com.alxy.advisoryservice.dto.OptimizationProduct;
import com.alxy.advisoryservice.dto.PortfolioItemAllocation;
import com.alxy.advisoryservice.dto.ProductStats;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarkowitzPortfolioOptimizer implements PortfolioOptimizer {
    
    private static final double[] RISK_TOLERANCE = {0.1, 0.3, 0.5, 0.7, 0.9};
    
    @Override
    public OptimalPortfolio optimize(List<ProductStats> productStats, int riskLevel) {
        if (productStats == null || productStats.isEmpty()) {
            throw new IllegalArgumentException("Product stats cannot be null or empty");
        }
        // 验证风险等级
        int adjustedRiskLevel = Math.max(1, Math.min(5, riskLevel));
        double lambda = RISK_TOLERANCE[adjustedRiskLevel - 1];
        
        // 准备优化问题
        List<OptimizationProduct> products = productStats.stream()
            .map(ps -> new OptimizationProduct(ps.getProductId(), ps.getExpectedReturn(), ps.getRisk()))
            .collect(Collectors.toList());
        return simpleHeuristicOptimization(products, lambda);
    }
    
    private OptimalPortfolio simpleHeuristicOptimization(List<OptimizationProduct> products, double lambda) {
        OptimalPortfolio portfolio = new OptimalPortfolio();
        
        // 按夏普比率排序 (简化处理)
        products.sort(Comparator.comparingDouble(p -> (p.getExpectedReturn() - lambda * p.getRisk())));
        Collections.reverse(products);
        
        // 分配权重 (前3个产品分配主要权重)
        double totalWeight = 0;
        for (int i = 0; i < Math.min(3, products.size()); i++) {
            OptimizationProduct product = products.get(i);
            double weight = 0.7 / (i + 1); // 第一个产品最多
            portfolio.addItem(new PortfolioItemAllocation(
                product.getProductId(),
                product.getProductId(), // 实际应用中应该获取产品名称
                weight * 100,
                BigDecimal.valueOf(weight * 10000), // 示例金额
                "TYPE" // 实际应用中应该获取产品类型
            ));
            totalWeight += weight;
        }
        
        // 剩余权重分配给其他产品
        if (products.size() > 3 && totalWeight < 1.0) {
            double remainingWeight = 1.0 - totalWeight;
            double equalWeight = remainingWeight / (products.size() - 3);
            
            for (int i = 3; i < products.size(); i++) {
                OptimizationProduct product = products.get(i);
                portfolio.addItem(new PortfolioItemAllocation(
                    product.getProductId(),
                    product.getProductId(),
                    equalWeight * 100,
                    BigDecimal.valueOf(equalWeight * 10000),
                    "TYPE"
                ));
            }
        }
        
        // 计算组合预期收益和风险
        double portfolioReturn = 0;
        double portfolioRisk = 0;
        
        for (PortfolioItemAllocation item : portfolio.getItems()) {
            double weight = item.getAllocationPercentage() / 100;
            OptimizationProduct product = products.stream()
                .filter(p -> p.getProductId().equals(item.getProductCode()))
                .findFirst()
                .orElseThrow();
            
            portfolioReturn += weight * product.getExpectedReturn();
            portfolioRisk += weight * product.getRisk();
        }
        
        portfolio.setExpectedReturn(portfolioReturn);
        portfolio.setRiskScore(portfolioRisk);
        portfolio.setRiskLevel((int) (lambda * 5)); // 映射回1-5的风险等级
        
        return portfolio;
    }
}