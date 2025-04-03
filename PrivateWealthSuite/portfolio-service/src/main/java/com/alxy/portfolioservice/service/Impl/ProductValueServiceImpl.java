package com.alxy.portfolioservice.service.Impl;

import com.alxy.portfolioservice.entiy.ProductValueHistory;
import com.alxy.portfolioservice.repository.ProductValueHistoryRepository;
import com.alxy.portfolioservice.service.ProductValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class ProductValueServiceImpl implements ProductValueService {
    @Autowired
    private ProductValueHistoryRepository repository;

    // 获取理财产品的价值历史记录
    public List<ProductValueHistory> getValueHistory(String itemId) {
        return repository.findByItemId(itemId);
    }

}
