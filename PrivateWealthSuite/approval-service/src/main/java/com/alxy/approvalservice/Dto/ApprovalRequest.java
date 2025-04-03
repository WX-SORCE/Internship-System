package com.alxy.approvalservice.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ApprovalRequest {
    @NotEmpty(message = "交易ID列表不能为空")
    private List<String> tradeIds;
    
    @NotBlank(message = "用户ID不能为空")
    private String userId;
}