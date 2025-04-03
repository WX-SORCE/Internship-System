package com.alxy.advisoryservice.controller;

import com.alxy.advisoryservice.dto.Result;
import com.alxy.advisoryservice.dto.Trade;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "trade-service", path = "/v1/trade")
public interface TradeFeign {
    @PostMapping("/createTrade")
    public Result<?> createTrade(@RequestBody Trade trade);
}
