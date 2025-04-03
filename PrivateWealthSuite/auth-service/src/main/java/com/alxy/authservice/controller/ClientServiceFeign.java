package com.alxy.authservice.controller;


import com.alxy.authservice.entity.Client;
import com.alxy.authservice.dto.Result;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "client-service", path = "/v1/client")
public interface ClientServiceFeign {

    @PostMapping
    Result<?> createClient(@Valid @RequestBody Client client);

    @GetMapping("/{clientId}")
    Result<?> getClientById(@PathVariable("clientId") String clientId);

    @PostMapping("/findByKycDateBefore")
    Result<List<Client>> findByKycDueDateBefore(@RequestParam LocalDate date);

    @GetMapping("/getByUserId")
    Result<Client> getByUserId(@RequestParam String userId);

    @PutMapping("/updateStatusById")
    int updateStatusById(@RequestParam String clientId ,@RequestParam String Status);
}