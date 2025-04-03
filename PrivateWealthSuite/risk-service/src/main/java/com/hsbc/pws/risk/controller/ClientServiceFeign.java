package com.hsbc.pws.risk.controller;

import com.hsbc.pws.common.response.Result;
import com.hsbc.pws.risk.pojo.CustomerRisk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "client-service", path = "/v1/client")
public interface ClientServiceFeign {

    @PutMapping("/updateRiskInfo")
    public Result<?> updateRiskInfo(@RequestBody CustomerRisk customerRisk );
}