package com.hsbc.pws.risk.controller;

import com.hsbc.pws.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "auth-service", path = "/v1/auth")
public interface AuthServiceFeign {

    @PutMapping("/updateLevel")
    Result<?> updateLevel(@RequestParam String clientId, @RequestParam Integer identityLevel , @RequestParam String Status);
}