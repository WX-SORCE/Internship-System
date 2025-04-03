package com.alxy.advisoryservice.recommend;

import com.alxy.advisoryservice.dto.ProductPrediction;
import com.alxy.advisoryservice.entity.ProductValueHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReturnPredictor {
    /**
     * 预测产品未来收益
     */
    ProductPrediction predict(String productId, List<ProductValueHistory> historicalData);
}