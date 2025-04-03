package com.alxy.portfolioservice.controller;

import com.alxy.portfolioservice.entiy.PortfolioItems;
import com.alxy.portfolioservice.entiy.Portfolios;
import com.alxy.portfolioservice.entiy.PortfoliosVo.PortfolioRatioVo;
import com.alxy.portfolioservice.entiy.PortfoliosVo.PortfoliosVo;
import com.alxy.portfolioservice.repository.PortfoliosRepository;
import com.alxy.portfolioservice.result.Result;
import com.alxy.portfolioservice.service.PortfolioService;
import com.alxy.portfolioservice.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @description:
 * @author: 宋枝波
 * @date: 2025-04-02 15:55
 */
@Slf4j
@RestController
@RequestMapping("/v1/portfolios")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    // 获取客户的投资组合列表
    @GetMapping("/client/{clientId}")
    public Result  getPortfoliosByClientId(@PathVariable String clientId) {
        List<PortfoliosVo> portfoliosVosList = new ArrayList<>();
        List<Portfolios> s= portfolioService.getPortfoliosByClientId(clientId);
        s.forEach(portfolio -> {
            portfolio.getPortfolioItems().forEach(portfolioItem -> {
                PortfoliosVo build = PortfoliosVo.builder().productName(portfolioItem.getProductName())
                        .unitValue(portfolioItem.getUnitValue())
                        .amount(portfolioItem.getAmount())
                        .createdAt(portfolioItem.getCreatedAt())
                        .type(portfolioItem.getType()).build();
                portfoliosVosList.add(build);
            });
        });

        return Result.success(portfoliosVosList);

    }

    // 获取投资组合的持仓明细
    @GetMapping("/{portfolioId}/items")
    public Result getPortfolioItemsByPortfolioId(@PathVariable String portfolioId) {
        List<PortfolioItems> portfolioItemsByPortfolioId = portfolioService.getPortfolioItemsByPortfolioId(portfolioId);
        return Result.success(portfolioItemsByPortfolioId);
    }

    // 计算持仓市值
    @GetMapping("/{portfolioId}/market-value")
    public Result calculateMarketValue(@PathVariable String portfolioId) {
        List<PortfolioItems> items = portfolioService.getPortfolioItemsByPortfolioId(portfolioId);
        return Result.success(portfolioService.calculateMarketValue(items));
    }

    // 计算盈亏情况
    @GetMapping("/{portfolioId}/profit-loss")
    public Result calculateProfitLoss(@PathVariable String portfolioId, @RequestParam BigDecimal initialInvestment) {
        List<PortfolioItems> items = portfolioService.getPortfolioItemsByPortfolioId(portfolioId);
        return Result.success(portfolioService.calculateProfitLoss(items, initialInvestment));
    }

    // 计算组合比例
    @GetMapping("/{portfolioId}/ratio")
    public Result calculatePortfolioRatio(@PathVariable String portfolioId) {
        List<PortfolioItems> items = portfolioService.getPortfolioItemsByPortfolioId(portfolioId);
        return Result.success(portfolioService.calculatePortfolioRatio(items));
    }

    // 客户经理创建组合草案
    @PostMapping("/draft")
    public Result createPortfolioDraft(@RequestBody Portfolios portfolio) {
        return Result.success(portfolioService.createPortfolioDraft(portfolio));
    }

    // 计算同一种 type 的 ProductValueHistory 近七天每天金额的平均值
    @GetMapping("/client/{clientId}/average-value-last-seven-days")
    public Result calculateAverageValueByTypeLastSevenDays(@PathVariable String clientId) {
        Map<String, List<Map<Date, Double>>> result = portfolioService.calculateAverageValueByTypeLastSevenDays(clientId);
        // 格式化日期
        Map<String, List<Map<String, Double>>> formattedResult = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<String, List<Map<Date, Double>>> entry : result.entrySet()) {
            String type = entry.getKey();
            List<Map<Date, Double>> values = entry.getValue();
            List<Map<String, Double>> formattedValues = new ArrayList<>();
            for (Map<Date, Double> valueMap : values) {
                for (Map.Entry<Date, Double> dateEntry : valueMap.entrySet()) {
                    String formattedDate = dateFormat.format(dateEntry.getKey());
                    Map<String, Double> formattedMap = new HashMap<>();
                    formattedMap.put(formattedDate, dateEntry.getValue());
                    formattedValues.add(formattedMap);
                }
            }
            formattedResult.put(type, formattedValues);
        }

        return Result.success(formattedResult);
    }

    //总资产变化
    @GetMapping("/client/{clientId}/average-value-last-seven-days-new")
    public Result calculateAverageValueLastSevenDaysNew(@PathVariable String clientId) {
        Map<Date, BigDecimal> result = portfolioService.calculateAverageValueLastSevenDays(clientId);
        Map<String, BigDecimal> formattedResult = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<Date, BigDecimal> entry : result.entrySet()) {
            String formattedDate = dateFormat.format(entry.getKey());
            formattedResult.put(formattedDate, entry.getValue());
        }
        return Result.success(formattedResult);
    }

    // 计算前五收益高的投资组合及其收益率
    @GetMapping("/client/{clientId}/top-five-portfolio-returns")
    public Result calculateTopFivePortfolioReturns(@PathVariable String clientId) {
        List<Map<String, Object>> result = portfolioService.calculateTopFivePortfolioReturns(clientId);
        return Result.success(result);
    }

    // 计算投资组合的风险率并返回前五的组合
    @GetMapping("/client/{clientId}/top-five-risk-ratios")
    public Result calculateTopFiveRiskRatios(@PathVariable String clientId) {
        List<Map<String, Object>> result = portfolioService.calculateTopFiveRiskRatios(clientId);
        return Result.success(result);
    }

    // 计算理财指数
    @GetMapping("/client/{clientId}/financial-index")
    public Result calculateFinancialIndex(@PathVariable String clientId) {
        int index = portfolioService.calculateFinancialIndex(clientId);
        return Result.success(index);
    }


    // 计算组合比例，接受 clientId 作为参数
    @GetMapping("/{clientId}/ratios")
    public Result calculatePortfolioRatios(@PathVariable String clientId) {
        List<PortfolioRatioVo> ratioVos = portfolioService.calculatePortfolioRatioByClientId(clientId);
        return Result.success(ratioVos);
    }
}



