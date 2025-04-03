package com.alxy.approvalservice.Controller;

import com.alxy.approvalservice.Dto.Result;
import com.alxy.approvalservice.Dto.Trade;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "trade-service", path = "/v1/trade")
public interface TradeControllerFeign {

    @GetMapping("/getTradesByClientId")
    public Result<List<Trade>> getTradesByClientId(@RequestParam String clientId);

    @PutMapping("/{tradeId}/status")
    public Result<?> changeTradeStatus(
            @RequestParam String newStatus, @PathVariable String tradeId);

    @GetMapping("/getClientIdByTradeId")
    public Result<Trade> getByTradeId(@RequestParam String tradeId);
}


