package com.alxy.tradeservice.controller;


import com.alxy.tradeservice.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@FeignClient(name = "approval-service", path = "/v1/approval")
public interface ApprovalFeign {
    @PostMapping("/create")
    Result<?> create(@RequestParam String treadId, @RequestParam String advisorId);
}