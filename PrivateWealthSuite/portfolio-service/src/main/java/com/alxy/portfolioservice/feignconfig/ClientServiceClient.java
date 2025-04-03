package com.alxy.portfolioservice.feignconfig;

import com.alxy.portfolioservice.result.Result;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "client-service")
public interface ClientServiceClient {
    @GetMapping("/{id}")
    public Result getClientById(@PathVariable String clientId);
}
