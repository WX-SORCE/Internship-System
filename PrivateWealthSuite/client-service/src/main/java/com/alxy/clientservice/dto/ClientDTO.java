package com.alxy.clientservice.dto;

import lombok.Data;


@Data
public class ClientDTO {
    private Integer incomeLevel;
    private Integer riskLevel;
    private Integer totalAssets;
}
