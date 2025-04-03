package com.alxy.advisoryservice.recommend;

import com.alxy.advisoryservice.dto.ProductPrediction;
import com.alxy.advisoryservice.entity.ProductValueHistory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class SimpleMovingAveragePredictor implements ReturnPredictor {
    
    private static final int WINDOW_SIZE = 30; // 30天移动平均
    
    @Override
    public ProductPrediction predict(String productId, List<ProductValueHistory> historicalData) {
        if (historicalData == null || historicalData.isEmpty()) {
            throw new IllegalArgumentException("Historical data cannot be null or empty");
        }
        // 按日期排序
        historicalData.sort(Comparator.comparing(ProductValueHistory::getDate));
        // 计算简单移动平均收益率
        double sumReturn = 0;
        int count = 0;
        int startIndex = Math.max(0, historicalData.size() - WINDOW_SIZE);
        for (int i = startIndex; i < historicalData.size(); i++) {
            ProductValueHistory record = historicalData.get(i);
            if (record.getReturnRate() != null) {
                sumReturn += record.getReturnRate().doubleValue();
                count++;
            }
        }
        double avgReturn = count > 0 ? sumReturn / count : 0;
        // 简单风险估算 (使用历史波动率)
        double sumSquaredDeviation = 0;
        for (int i = startIndex; i < historicalData.size(); i++) {
            ProductValueHistory record = historicalData.get(i);
            if (record.getReturnRate() != null) {
                double deviation = record.getReturnRate().doubleValue() - avgReturn;
                sumSquaredDeviation += deviation * deviation;
            }
        }
        double volatility = count > 1 ? Math.sqrt(sumSquaredDeviation / (count - 1)) : 0;
        return new ProductPrediction(
            productId,
            historicalData.get(0).getType(),
            avgReturn,
            volatility,
            new Date()
        );
    }
}