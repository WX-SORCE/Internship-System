package com.alxy.portfolioservice.controller;

import com.alxy.portfolioservice.entiy.ProductValueHistory;
import com.alxy.portfolioservice.service.ProductValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * @description:
 * @author: 宋枝波
 * @date: 2025-04-02 15:55
 */
@RestController
public class ProductValueController {
    @Autowired
    private ProductValueService service;

    // 获取理财产品的价值历史记录
    @GetMapping("/products/{productId}/value-history")
    public List<ProductValueHistory> getValueHistory(@PathVariable String itemId) {
        return service.getValueHistory(itemId);
    }
}