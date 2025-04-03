package com.alxy.advisoryservice.controller;


import com.alxy.advisoryservice.dto.Client;
import com.alxy.advisoryservice.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "client-service", path = "/v1/client")
public interface ClientFeign {
    @GetMapping("/getByUserId")
    Result<Client> getClientById(@RequestParam String userId);

    @PutMapping("/{clientId}/pay")
    Result<?> pay(@PathVariable String clientId,
                         @RequestParam BigDecimal total);
}
