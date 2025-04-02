package com.alxy.clientservice.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;



@Data
public class CustomerRisk implements Serializable {

	private String clientId;

	private Integer riskLevel;

	private LocalDate nextKycDueDate;

	private BigDecimal totalAssets;

	private String incomeLevel;

	private Integer score;
}
