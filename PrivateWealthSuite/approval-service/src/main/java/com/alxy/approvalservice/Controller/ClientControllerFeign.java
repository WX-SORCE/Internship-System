package com.alxy.approvalservice.Controller;

import com.alxy.approvalservice.Dto.Client;
import com.alxy.approvalservice.Dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "client-service", path = "/v1/client")
public interface ClientControllerFeign {
    @GetMapping("/getByUserId")
    public Result<Client> getByUserId(@RequestParam String userId);
    @GetMapping("/{clientId}")
    public Result<Client> getClientById(@PathVariable String clientId);

}
