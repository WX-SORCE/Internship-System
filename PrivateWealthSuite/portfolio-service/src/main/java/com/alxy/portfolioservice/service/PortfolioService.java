package com.alxy.portfolioservice.service;




import com.alxy.portfolioservice.entiy.PortfolioItems;
import com.alxy.portfolioservice.entiy.Portfolios;
import com.alxy.portfolioservice.entiy.PortfoliosVo.PortfolioRatioVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PortfolioService {
    // 获取客户的投资组合列表
    List<Portfolios> getPortfoliosByClientId(String clientId);

    // 获取投资组合的持仓明细
    List<PortfolioItems> getPortfolioItemsByPortfolioId(String portfolioId);

    // 计算持仓市值
    BigDecimal calculateMarketValue(List<PortfolioItems> items);

    // 计算盈亏情况
    BigDecimal calculateProfitLoss(List<PortfolioItems> items, BigDecimal initialInvestment);

    // 计算组合比例
    Map<String, BigDecimal> calculatePortfolioRatio(List<PortfolioItems> items);

    // 客户经理创建组合草案
    Portfolios createPortfolioDraft(Portfolios portfolio);

    // 计算同一种 type 的 ProductValueHistory 近七天每天金额的平均值
    Map<String, List<Map<Date, Double>>> calculateAverageValueByTypeLastSevenDays(String clientId);

    Map<Date, BigDecimal> calculateAverageValueLastSevenDays(String clientId);

    // 计算前五收益高的投资组合及其收益率
    List<Map<String, Object>> calculateTopFivePortfolioReturns(String clientId);

    List<Map<String, Object>> calculateTopFiveRiskRatios(String clientId);

    int calculateFinancialIndex(String clientId);

    List<PortfolioRatioVo> calculatePortfolioRatioByClientId(String clientId);
}
