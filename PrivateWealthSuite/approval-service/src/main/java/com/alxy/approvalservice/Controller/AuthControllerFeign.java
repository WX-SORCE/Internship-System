package com.alxy.approvalservice.Controller;

import com.alxy.approvalservice.Dto.Result;
import com.alxy.approvalservice.Dto.User;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service", path = "/v1/auth")
public interface AuthControllerFeign {

    @GetMapping("/getUser")
    Result<User> getUser(@RequestParam String userId);
}