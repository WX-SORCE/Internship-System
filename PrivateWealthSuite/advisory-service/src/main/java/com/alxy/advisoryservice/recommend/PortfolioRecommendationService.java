package com.alxy.advisoryservice.recommend;

import com.alxy.advisoryservice.config.RecommendationNotFoundException;
import com.alxy.advisoryservice.dto.OptimalPortfolio;
import com.alxy.advisoryservice.dto.ProductPrediction;
import com.alxy.advisoryservice.dto.ProductStats;
import com.alxy.advisoryservice.entity.ProductValueHistory;
import com.alxy.advisoryservice.dto.PortfolioItem; // 添加这行导入
import com.alxy.advisoryservice.entity.Recommendation;
import com.alxy.advisoryservice.entity.RecommendedItem;
import com.alxy.advisoryservice.repository.PortfolioItemRepository;
import com.alxy.advisoryservice.repository.ProductValueHistoryRepository;
import com.alxy.advisoryservice.repository.RecommendationRepository;
import com.alxy.advisoryservice.repository.RecommendedItemRepository;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // 使用SLF4J的Logger
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PortfolioRecommendationService {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioRecommendationService.class); // 修正Logger类型

    private final ProductValueHistoryRepository historyRepository;
    private final PortfolioItemRepository portfolioItemRepository;
    private final RecommendationRepository recommendationRepository;
    @Resource
    private final RecommendedItemRepository recommendedItemRepository;
    private final ReturnPredictor returnPredictor;
    private final PortfolioOptimizer portfolioOptimizer;

    @Autowired
    public PortfolioRecommendationService(ProductValueHistoryRepository historyRepository,
                                          PortfolioItemRepository portfolioItemRepository,
                                          RecommendationRepository recommendationRepository, RecommendedItemRepository recommendedItemRepository,
                                          ReturnPredictor returnPredictor,
                                          PortfolioOptimizer portfolioOptimizer) {
        this.historyRepository = historyRepository;
        this.portfolioItemRepository = portfolioItemRepository;
        this.recommendationRepository = recommendationRepository;
        this.recommendedItemRepository = recommendedItemRepository;
        this.returnPredictor = returnPredictor;
        this.portfolioOptimizer = portfolioOptimizer;
    }

    public Recommendation generateRecommendation(String clientId, String advisorId, int riskLevel) {
        logger.info("Generating recommendation for client: {}, risk level: {}", clientId, riskLevel); // 修复日志参数

        List<ProductValueHistory> historyData = loadHistoricalData();
        Map<String, ProductPrediction> predictions = predictProductReturns(historyData);
        OptimalPortfolio optimalPortfolio = optimizePortfolio(predictions, riskLevel);
        return createAndSaveRecommendation(optimalPortfolio, clientId, advisorId);
    }

    private List<ProductValueHistory> loadHistoricalData() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(12); // 使用新的时间API
        return historyRepository.findByDateBetween(startDate, endDate);
    }

    private Map<String, ProductPrediction> predictProductReturns(List<ProductValueHistory> historyData) {
        logger.debug("Predicting returns for {} historical records", historyData.size());

        return historyData.stream()
                .collect(Collectors.groupingBy(ProductValueHistory::getItemId))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> returnPredictor.predict(entry.getKey(), entry.getValue())
                ));
    }

    private OptimalPortfolio optimizePortfolio(Map<String, ProductPrediction> predictions, int riskLevel) {
        logger.debug("Optimizing portfolio with {} products, risk level {}", predictions.size(), riskLevel);

        List<ProductStats> productStats = predictions.entrySet().stream()
                .map(entry -> new ProductStats(
                        entry.getKey(),
                        entry.getValue().getExpectedReturn(),
                        entry.getValue().getRisk(),
                        entry.getValue().getProductType()
                ))
                .collect(Collectors.toList());

        return portfolioOptimizer.optimize(productStats, riskLevel);
    }

    private Recommendation createAndSaveRecommendation(OptimalPortfolio portfolio,
                                                       String clientId,
                                                       String advisorId) {
        logger.info("Creating recommendation for portfolio with {} items", portfolio.getItems().size());

        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(UUID.randomUUID().toString());
        recommendation.setRecommendationName(generateRecommendationName(portfolio.getRiskLevel()));
        recommendation.setRiskLevel(portfolio.getRiskLevel());
        recommendation.setYieldRate(calculateYieldRate(portfolio.getExpectedReturn()));
        recommendation.setClientId(clientId);
        recommendation.setAdvisorId(advisorId);
        recommendation.setCreatedAt(new Date());

        portfolio.getItems().forEach(item -> {
            RecommendedItem recommendedItem = new RecommendedItem();
            recommendedItem.setItemId(UUID.randomUUID().toString());
            recommendedItem.setProductCode(item.getProductCode());
            recommendedItem.setProductName(getProductName(item.getProductCode()));
            recommendedItem.setAmount(item.getRecommendedAmount());
            recommendedItem.setProductPercent(BigDecimal.valueOf(item.getAllocationPercentage()));
            recommendedItem.setRecommendationId(recommendation.getRecommendationId());
            recommendedItemRepository.save(recommendedItem);
        });

        return recommendationRepository.save(recommendation);
    }

    private String generateRecommendationName(int riskLevel) {
        String riskName = switch (riskLevel) {
            case 1 -> "保守型";
            case 2 -> "稳健型";
            case 3 -> "平衡型";
            case 4 -> "成长型";
            case 5 -> "进取型";
            default -> "自定义";
        };
        return riskName + "组合-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private int calculateYieldRate(double expectedReturn) {
        return (int) Math.round(expectedReturn * 100 * 100);
    }

    private String getProductName(String productCode) {
        return portfolioItemRepository.findByProductCode(productCode)
                .stream()
                .findFirst()
                .map(PortfolioItem::getProductName)
                .orElse(productCode);
    }

    public List<Recommendation> getClientRecommendations(String clientId) {
        return recommendationRepository.findByClientIdOrderByCreatedAtDesc(clientId);
    }

    public Recommendation getRecommendation(String recommendationId) {
        return recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new RecommendationNotFoundException(recommendationId));
    }

    public Recommendation updateRecommendationStatus(String recommendationId, boolean accepted, String feedback) {
        Recommendation recommendation = getRecommendation(recommendationId);
        recommendation.setAccepted(accepted);
        recommendation.setFeedback(feedback);
        return recommendationRepository.save(recommendation);
    }
}