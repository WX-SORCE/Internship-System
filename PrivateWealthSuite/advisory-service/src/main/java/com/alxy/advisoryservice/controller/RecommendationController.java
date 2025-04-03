package com.alxy.advisoryservice.controller;

import com.alxy.advisoryservice.dto.Recommendations;
import com.alxy.advisoryservice.dto.RecommendedItems;
import com.alxy.advisoryservice.dto.Result;
import com.alxy.advisoryservice.dto.Trade;
import com.alxy.advisoryservice.entity.Recommendation;
import com.alxy.advisoryservice.entity.RecommendedItem;
import com.alxy.advisoryservice.recommend.PortfolioRecommendationService;
import com.alxy.advisoryservice.repository.RecommendationRepository;
import com.alxy.advisoryservice.repository.RecommendedItemRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/advisory")
public class RecommendationController {

    @Autowired
    RecommendationRepository recommendationRepository;
    @Autowired
    RecommendedItemRepository recommendedItemRepository;
    @Resource
    private ClientFeign clientFeign;
    @Resource
    private TradeFeign tradeFeign;

    @Autowired
    PortfolioRecommendationService portfolioRecommendationService;

    // 获取推荐列表
    @PostMapping("/getRecommendList")
    public Result<?> getRecommendList(@RequestParam String clientId){
        List<Recommendation> recommendations = recommendationRepository.findRecommendationByClientId(clientId);
        return Result.success(recommendations);
    }

    // 根据推荐组合生成订单
    @PostMapping("/createTrades")
    public Result<?> createTrades(@RequestParam String recommendationId){
        Recommendation recommendation = recommendationRepository.findRecommendationByRecommendationId(recommendationId);
        List<RecommendedItem> recommendedItems = recommendedItemRepository.findRecommendedItemsByRecommendationId(recommendationId);
        for (RecommendedItem recommendedItem :recommendedItems){
            Trade trade = new Trade();
            trade.setClientId(recommendation.getClientId());
            trade.setProductCode(recommendedItem.getProductCode());
            trade.setAmount(recommendedItem.getAmount());
            tradeFeign.createTrade(trade);
        }
        return Result.success();
    }



    @PostMapping("/getRecommendItemsByClientId")
    public Result<List<Recommendations>> getRecommendItemsByClientId(@RequestParam String clientId) {
        // 1. 初始化返回列表
        List<Recommendations> recommendationsList = new ArrayList<>();
        // 2. 查询推荐列表
        List<Recommendation> recommendations = recommendationRepository.findRecommendationByClientId(clientId);
        if (recommendations == null || recommendations.isEmpty()) {
            return Result.success(Collections.emptyList());
        }
        // 3. 转换推荐列表
        for (Recommendation recommendation : recommendations) {
            Recommendations recommendationsDto = new Recommendations();
            recommendationsDto.setRecommendationId(recommendation.getRecommendationId());
            recommendationsDto.setRecommendationName(recommendation.getRecommendationName());
            recommendationsDto.setRiskLevel(recommendation.getRiskLevel());
            recommendationsDto.setYieldRate(recommendation.getYieldRate());
            // 4. 查询并转换推荐项
            List<RecommendedItems> recommendedItemsList = recommendedItemRepository
                    .findRecommendedItemsByRecommendationId(recommendation.getRecommendationId())
                    .stream()
                    .map(item -> {
                        RecommendedItems recommendedItemDto = new RecommendedItems();
                        recommendedItemDto.setProductName(item.getProductName());
                        recommendedItemDto.setProductPercent(item.getProductPercent());
                        return recommendedItemDto;
                    })
                    .collect(Collectors.toList());

            recommendationsDto.setRecommendedItems(recommendedItemsList);
            recommendationsList.add(recommendationsDto);
        }
        return Result.success(recommendationsList);
    }

    @PostMapping("/getRecommendItem")
    public Result<?> getRecommendItem(@RequestParam String recommendId){
        List<RecommendedItem> recommendedItems = recommendedItemRepository.findRecommendedItemsByRecommendationId(recommendId);
        return Result.success(recommendedItems);
    }

    @PostMapping("/creatRecommendList")
    public Result<?> creatRecommendList(@RequestParam String clientId, @RequestParam String userId){
        // 调用推荐生成服务
        Integer riskLevel = clientFeign.getClientById(clientId).getData().getRiskLevel();
        portfolioRecommendationService.generateRecommendation(clientId,userId,riskLevel);
        return Result.success();
    }

}