package com.alxy.portfolioservice.service.Impl;

import com.alxy.portfolioservice.entiy.PortfolioItems;
import com.alxy.portfolioservice.entiy.Portfolios;
import com.alxy.portfolioservice.entiy.PortfoliosVo.PortfolioRatioVo;
import com.alxy.portfolioservice.entiy.ProductValueHistory;
import com.alxy.portfolioservice.repository.PortfolioItemsRepository;
import com.alxy.portfolioservice.repository.PortfoliosRepository;
import com.alxy.portfolioservice.repository.ProductValueHistoryRepository;
import com.alxy.portfolioservice.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 20260
 */
@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfoliosRepository portfoliosRepository;

    @Autowired
    private PortfolioItemsRepository portfolioItemsRepository;

    @Autowired
    private ProductValueHistoryRepository productValueHistoryRepository;

    @Override
    public List<Portfolios> getPortfoliosByClientId(String clientId) {
        return portfoliosRepository.findByClientId(clientId);
    }

    @Override
    public List<PortfolioItems> getPortfolioItemsByPortfolioId(String portfolioId) {
        return portfolioItemsRepository.findByPortfoliosId(portfolioId);
    }

    @Override
    public BigDecimal calculateMarketValue(List<PortfolioItems> items) {
        BigDecimal totalValue = BigDecimal.ZERO;
        for (PortfolioItems item : items) {
            totalValue = totalValue.add(item.getAmount().multiply(item.getUnitValue()));
        }
        return totalValue;
    }

    @Override
    public BigDecimal calculateProfitLoss(List<PortfolioItems> items, BigDecimal initialInvestment) {
        BigDecimal marketValue = calculateMarketValue(items);
        return marketValue.subtract(initialInvestment);
    }

    @Override
    public Map<String, BigDecimal> calculatePortfolioRatio(List<PortfolioItems> items) {
        Map<String, BigDecimal> ratioMap = new HashMap<>();
        BigDecimal totalValue = calculateMarketValue(items);
        if (totalValue.compareTo(BigDecimal.ZERO) == 0) {
            return ratioMap;
        }
        for (PortfolioItems item : items) {
            BigDecimal itemValue = item.getAmount().multiply(item.getUnitValue());
            BigDecimal ratio = itemValue.divide(totalValue, 4, BigDecimal.ROUND_HALF_UP);
            ratioMap.put(item.getProductCode(), ratio);
        }
        return ratioMap;
    }

    @Override
    public Portfolios createPortfolioDraft(Portfolios portfolio) {
        // 检查客户端是否存在
        String clientId = portfolio.getClientId();


//        boolean clientExists = clientRepository.existsById(clientId);
//        if (!clientExists) {
//            throw new IllegalArgumentException("客户端 ID 不存在: " + clientId + "，请先创建客户端记录。");
//        }

        portfolio.setCreatedAt(new Date());
        // 计算总价值，如果 portfolioItems 为 null，将总价值设置为 0
        List<PortfolioItems> items = portfolio.getPortfolioItems();
        BigDecimal totalValue = items != null ? calculateMarketValue(items) : BigDecimal.ZERO;
        portfolio.setTotalValue(totalValue);

        try {
            return portfoliosRepository.save(portfolio);
        } catch (DataIntegrityViolationException e) {
            // 捕获数据完整性违规异常
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                // 处理外键约束违规异常
                throw new IllegalArgumentException("数据插入失败，可能存在外键约束问题，请检查客户端 ID。", e);
            }
            // 处理其他数据完整性问题
            throw new RuntimeException("数据插入失败，请检查输入数据。", e);
        }
    }


//    Portfolios更具clientid找到所有Portfolios
//    PortfolioItems 更具portfoliosId找到所有PortfolioItems
//    PortfolioItems 按type分类
//    计算同一种type的ProductValueHistory的近七天（包括今天）每天金额的平均值，返回日期，金额俩俩对应
//    PortfolioItems 按type分类的集合
@Override
public Map<String, List<Map<Date, Double>>> calculateAverageValueByTypeLastSevenDays(String clientId) {
    // 根据 clientId 找到所有的 Portfolios
    List<Portfolios> portfolios = getPortfoliosByClientId(clientId);

    // 计算近七天的起始日期
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR, -6);
    Date startDate = calendar.getTime();

    // 按 type 分组存储 itemId
    Map<String, List<String>> typeToItemIds = new HashMap<>();
    for (Portfolios portfolio : portfolios) {
        List<PortfolioItems> items = getPortfolioItemsByPortfolioId(portfolio.getPortfolioId());
        for (PortfolioItems item : items) {
            typeToItemIds.computeIfAbsent(item.getType(), k -> new ArrayList<>()).add(item.getItemId());
        }
    }

    // 存储每种 type 对应的日期和平均金额
    Map<String, List<Map<Date, Double>>> result = new HashMap<>();

    // 遍历每种 type，计算近七天每天的平均金额
    for (Map.Entry<String, List<String>> entry : typeToItemIds.entrySet()) {
        String type = entry.getKey();
        List<String> itemIds = entry.getValue();

        // 存储每天的总金额和记录数
        Map<Date, List<Double>> dateToValues = new HashMap<>();

        // 遍历 itemId 获取近七天的记录
        for (String itemId : itemIds) {
            List<ProductValueHistory> histories = productValueHistoryRepository.findByItemIdsAndDateAfter(Collections.singletonList(itemId), startDate);
            for (ProductValueHistory history : histories) {
                Date date = history.getDate();
                dateToValues.computeIfAbsent(date, k -> new ArrayList<>()).add(history.getValue());
            }
        }

        // 计算每天的平均金额
        List<Map<Date, Double>> averageValuesList = new ArrayList<>();
        for (Map.Entry<Date, List<Double>> dateEntry : dateToValues.entrySet()) {
            Date date = dateEntry.getKey();
            List<Double> values = dateEntry.getValue();
            double total = 0;
            for (double value : values) {
                total += value;
            }
            double average = total / values.size();
            Map<Date, Double> averageValueMap = new HashMap<>();
            averageValueMap.put(date, average);
            averageValuesList.add(averageValueMap);
        }

        // 按日期升序排序
        averageValuesList.sort(Comparator.comparing(m -> new ArrayList<>(m.keySet()).get(0)));

        result.put(type, averageValuesList);
    }

    return result;
}

    @Override
    public Map<Date, BigDecimal> calculateAverageValueLastSevenDays(String clientId) {
        // 根据 clientId 找到所有的 Portfolios
        List<Portfolios> portfolios = portfoliosRepository.findByClientId(clientId);

        // 存储所有相关的 itemId
        List<String> itemIds = new ArrayList<>();
        for (Portfolios portfolio : portfolios) {
            List<PortfolioItems> items = portfolioItemsRepository.findByPortfoliosId(portfolio.getPortfolioId());
            for (PortfolioItems item : items) {
                itemIds.add(item.getItemId());
            }
        }

        // 计算近七天的起始日期和结束日期
        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        Date startDate = calendar.getTime();

        // 查询指定 itemIds 在指定日期范围内的平均价格
        List<Object[]> results = productValueHistoryRepository.findAverageValueByItemIdsAndDateRange(itemIds, startDate, endDate);

        // 存储日期和平均价格的映射
        Map<Date, BigDecimal> averageValues = new LinkedHashMap<>();
        for (Object[] result : results) {
            Date date = (Date) result[0];
            BigDecimal averageValue = ((Number) result[1]).doubleValue() > 0 ? BigDecimal.valueOf(((Number) result[1]).doubleValue()) : BigDecimal.ZERO;
            averageValues.put(date, averageValue);
        }

        return averageValues;
    }

    @Override
    public List<Map<String, Object>> calculateTopFivePortfolioReturns(String clientId) {
        // 根据 clientId 找到所有的 Portfolios
        List<Portfolios> portfolios = getPortfoliosByClientId(clientId);

        // 存储每个投资组合的名称和收益率
        List<Map<String, Object>> portfolioReturns = new ArrayList<>();

        for (Portfolios portfolio : portfolios) {
            // 根据 portfolioId 找到所有的 PortfolioItems
            List<PortfolioItems> items = getPortfolioItemsByPortfolioId(portfolio.getPortfolioId());

            // 计算初始投资金额
            BigDecimal initialInvestment = calculateInitialInvestment(items);

            // 计算当前市值
            BigDecimal currentMarketValue = calculateCurrentMarketValue(items);

            // 计算收益率
            BigDecimal returnRate = calculateReturnRate(initialInvestment, currentMarketValue);

            // 存储组合名称和收益率
            Map<String, Object> portfolioReturn = new HashMap<>();
            portfolioReturn.put("portfolioName", portfolio.getName());
            portfolioReturn.put("returnRate", returnRate);
            portfolioReturns.add(portfolioReturn);
        }

        // 按收益率降序排序
        portfolioReturns.sort((p1, p2) -> {
            BigDecimal returnRate1 = (BigDecimal) p1.get("returnRate");
            BigDecimal returnRate2 = (BigDecimal) p2.get("returnRate");
            return returnRate2.compareTo(returnRate1);
        });

        // 取前五收益高的组合
        return portfolioReturns.subList(0, Math.min(5, portfolioReturns.size()));
    }

    private BigDecimal calculateInitialInvestment(List<PortfolioItems> items) {
        BigDecimal initialInvestment = BigDecimal.ZERO;
        for (PortfolioItems item : items) {
            initialInvestment = initialInvestment.add(item.getAmount().multiply(item.getUnitValue()));
        }
        return initialInvestment;
    }

    private BigDecimal calculateCurrentMarketValue(List<PortfolioItems> items) {
        BigDecimal currentMarketValue = BigDecimal.ZERO;
        for (PortfolioItems item : items) {
            // 根据 itemId 找到最新的 ProductValueHistory 记录
            List<ProductValueHistory> histories = productValueHistoryRepository.findByItemId(item.getItemId());
            if (!histories.isEmpty()) {
                // 假设最新记录是列表中的最后一个
                ProductValueHistory latestHistory = histories.get(histories.size() - 1);
                currentMarketValue = currentMarketValue.add(item.getAmount().multiply(new BigDecimal(latestHistory.getValue())));
            }
        }
        return currentMarketValue;
    }

    private BigDecimal calculateReturnRate(BigDecimal initialInvestment, BigDecimal currentMarketValue) {
        if (initialInvestment.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return currentMarketValue.subtract(initialInvestment).divide(initialInvestment, 4, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<Map<String, Object>> calculateTopFiveRiskRatios(String clientId) {
        // 根据客户 ID 获取所有投资组合
        List<Portfolios> portfolios = portfoliosRepository.findByClientId(clientId);

        // 存储每个投资组合的名称和风险率
        List<Map<String, Object>> riskRatios = new ArrayList<>();

        // 遍历每个投资组合
        for (Portfolios portfolio : portfolios) {
            // 根据投资组合 ID 获取所有持仓明细
            List<PortfolioItems> items = portfolioItemsRepository.findByPortfoliosId(portfolio.getPortfolioId());

            // 计算该投资组合的总初始价值
            BigDecimal initialValue = calculateInitialValue(items);

            // 计算该投资组合的当前价值
            BigDecimal currentValue = calculateCurrentValue(items);

            // 计算风险率
            BigDecimal riskRatio = calculateRiskRatio(initialValue, currentValue);

            // 创建一个 Map 存储组合名称和风险率
            Map<String, Object> ratioMap = new HashMap<>();
            ratioMap.put("portfolioName", portfolio.getName());
            ratioMap.put("riskRatio", riskRatio);

            // 将该组合的信息添加到列表中
            riskRatios.add(ratioMap);
        }

        // 按风险率降序排序
        riskRatios.sort((a, b) -> {
            BigDecimal ratioA = (BigDecimal) a.get("riskRatio");
            BigDecimal ratioB = (BigDecimal) b.get("riskRatio");
            return ratioB.compareTo(ratioA);
        });

        // 取前五条记录
        return riskRatios.stream().limit(5).toList();
    }

    // 计算初始价值
    private BigDecimal calculateInitialValue(List<PortfolioItems> items) {
        BigDecimal initialValue = BigDecimal.ZERO;
        for (PortfolioItems item : items) {
            initialValue = initialValue.add(item.getAmount().multiply(item.getUnitValue()));
        }
        return initialValue;
    }

    // 计算当前价值
    private BigDecimal calculateCurrentValue(List<PortfolioItems> items) {
        BigDecimal currentValue = BigDecimal.ZERO;
        for (PortfolioItems item : items) {
            // 根据 item_id 获取最新的产品价值历史记录
            List<ProductValueHistory> histories = productValueHistoryRepository.findByItemIdOrderByDateDesc(item.getItemId());
            if (!histories.isEmpty()) {
                BigDecimal latestValue = BigDecimal.valueOf(histories.get(0).getValue());
                currentValue = currentValue.add(item.getAmount().multiply(latestValue));
            }
        }
        return currentValue;
    }

    // 计算风险率
    private BigDecimal calculateRiskRatio(BigDecimal initialValue, BigDecimal currentValue) {
        if (initialValue.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return currentValue.subtract(initialValue).divide(initialValue, 4, RoundingMode.HALF_UP);
    }
    @Override
    public int calculateFinancialIndex(String clientId) {
        // 根据 clientId 找到所有的 Portfolios
        List<Portfolios> portfolios = getPortfoliosByClientId(clientId);

        // 存储所有 itemId
        List<String> allItemIds = new ArrayList<>();

        // 遍历每个 Portfolios，找到所有的 PortfolioItems
        for (Portfolios portfolio : portfolios) {
            List<PortfolioItems> items = getPortfolioItemsByPortfolioId(portfolio.getPortfolioId());
            for (PortfolioItems item : items) {
                allItemIds.add(item.getItemId());
            }
        }

        // 根据 itemId 找到所有的 ProductValueHistory
        List<ProductValueHistory> histories = productValueHistoryRepository.findByItemIdsAndDateAfter(allItemIds, new Date(0));

        // 计算理财指数（这里简单假设根据历史价值的平均值来计算，你可以根据实际需求调整）
        BigDecimal totalValue = BigDecimal.ZERO;
        int count = 0;
        for (ProductValueHistory history : histories) {
            totalValue = totalValue.add(BigDecimal.valueOf(history.getValue()));
            count++;
        }

        if (count == 0) {
            return 1; // 如果没有数据，默认指数为 1
        }

        BigDecimal averageValue = totalValue.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);

        // 根据平均值计算理财指数，范围在 1 - 10
        int index = (int) Math.min(10, Math.max(1, averageValue.intValue() / 100));

        return index;
    }


    // 根据 clientId 计算组合比例
    public List<PortfolioRatioVo> calculatePortfolioRatioByClientId(String clientId) {
        // 根据 clientId 查询所有 Portfolio
        List<Portfolios> portfolios = portfoliosRepository.findByClientId(clientId);

        // 收集所有 PortfolioItems
        List<PortfolioItems> allItems = new ArrayList<>();
        for (Portfolios portfolio : portfolios) {
            allItems.addAll(portfolio.getPortfolioItems());
        }

        // 按 type 分组并计算每个 type 的总金额
        Map<String, BigDecimal> typeAmountMap = new HashMap<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PortfolioItems item : allItems) {
            String type = item.getType();
            BigDecimal amount = item.getAmount();
            typeAmountMap.put(type, typeAmountMap.getOrDefault(type, BigDecimal.ZERO).add(amount));
            totalAmount = totalAmount.add(amount);
        }

        // 计算每个 type 的金额占比
        List<PortfolioRatioVo> ratioVos = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : typeAmountMap.entrySet()) {
            String type = entry.getKey();
            BigDecimal amount = entry.getValue();
            BigDecimal ratio = totalAmount.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : amount.divide(totalAmount, 4, BigDecimal.ROUND_HALF_UP);
            PortfolioRatioVo ratioVo = new PortfolioRatioVo();
            ratioVo.setName(type);
            ratioVo.setValue(ratio);
            ratioVos.add(ratioVo);
        }

        return ratioVos;
    }
}