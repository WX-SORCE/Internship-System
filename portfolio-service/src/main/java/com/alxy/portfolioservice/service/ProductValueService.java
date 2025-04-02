package com.alxy.portfolioservice.service;

import com.alxy.portfolioservice.entiy.ProductValueHistory;

import java.util.List;

public interface ProductValueService {
    List<ProductValueHistory> getValueHistory(String itemId);
}
