package com.hsbc.pws.risk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerRisk implements Serializable {
	private static final long serialVersionUID = 3985130270763704241L;
	
	/**
	 * 客户编号
	 */
	private String clientId;
	/**
	 * 风险级别
	 */
	private String riskLevel;
	/**
	 * 下次KYC日期
	 */
	private LocalDate nextKycDueDate;
	/**
	 * 在本行资产总额
	 */
	@Builder.Default
	private BigDecimal totalAssets = new BigDecimal(0);
	/**
	 * 年收入等级
	 */
	@Builder.Default
	private Integer incomeLevel = 0;

	private Integer score = 0;
}
