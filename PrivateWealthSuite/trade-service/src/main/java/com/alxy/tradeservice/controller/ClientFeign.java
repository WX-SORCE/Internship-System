package com.alxy.tradeservice.controller;

import com.alxy.tradeservice.dto.Client;
import com.alxy.tradeservice.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@FeignClient(name = "client-service", path = "/v1/client")
public interface ClientFeign {
    @GetMapping("/{clientId}")
    Result<Client> getClientById(@PathVariable String clientId);

    @PutMapping("/{clientId}/pay")
    public Result<?> pay(@PathVariable String clientId,
                         @RequestParam BigDecimal total);
}
