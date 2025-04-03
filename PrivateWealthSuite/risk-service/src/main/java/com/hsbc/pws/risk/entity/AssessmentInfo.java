package com.hsbc.pws.risk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@DynamicInsert
@DynamicUpdate
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssessmentInfo implements Serializable {
	private static final long serialVersionUID = 4710587769514222670L;
	
	/**
	 * 评估表单编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer aiId;
	/**
	 * 客户编号
	 */
	@Column(nullable = false, length = 18)
	@NotBlank(message = "客户编号不能为空！")
	private String clientId;
	/**
	 * 年龄
	 */
	@Column(nullable = false)
	@ColumnDefault("1")
	@Builder.Default
	//@NotNull(message = "年龄不能为空！")
	private Integer ege = 0;
	/**
	 * 婚姻状况
	 * 0未婚 1已婚 2离异 3丧偶
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	//@NotNull(message = "婚姻状况不能为空！")
	private Integer maritalStatus = 0;
	/**
	 * 职业类型
	 * 0公务员 1国企职工 2民企职工 3自由职业者 4其他
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	//@NotNull(message = "职业类型不能为空！")
	private Integer occupationType = 0;
	/**
	 * 在本行资产总额
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	@NotNull(message = "在本行资产总额不能为空！")
	private BigDecimal totalAssets = new BigDecimal(0);
	/**
	 * 月收入
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	@NotNull(message = "月收入不能为空！")
	private Integer monthlyIncome = 0;
	/**
	 * 名下房产数量
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	//@NotNull(message = "名下房产数量不能为空！")
	private Integer propertiesOwned = 0;
	/**
	 * 名下车辆数量
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	//@NotNull(message = "名下车辆数量不能为空！")
	private Integer carOwned = 0;
	/**
	 * 每月固定支出额
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	//@NotNull(message = "每月固定支出额不能为空！")
	private Integer monthlyFixedExpenses = 0;
	/**
	 * 贷款额
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	//@NotNull(message = "贷款额不能为空！")
	private Integer loanAmount = 0;
	/**
	 * 其它负债额
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	//@NotNull(message = "其它负债额不能为空！")
	private Integer otherDebtAmount = 0;
	/**
	 * 信用额度
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	//@NotNull(message = "信用额度不能为空！")
	private Integer creditLimit = 0;
	/**
	 * 是否逾期
	 * 0否 1是
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	//@NotNull(message = "是否逾期不能为空！")
	private Integer pastDueStatus = 0;
	/**
	 * 是否不良记录
	 * 0否 1是
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	//@NotNull(message = "是否不良记录不能为空！")
	private Integer adverseCreditHistory = 0;
//	/**
//	 * 投资问卷得分
//	 */
//	@Column(nullable = false)
//	@ColumnDefault("0")
//	@Builder.Default
//	//@NotNull(message = "投资问卷得分不能为空！")
//	private Integer riskProfileScore = 0;
//	/**
//	 * 风险偏好问卷得分
//	 */
//	@Column(nullable = false)
//	@ColumnDefault("0")
//	@Builder.Default
//	//@NotNull(message = "风险偏好问卷得分不能为空！")
//	private Integer riskToleranceScore = 0;
	/**
	 * 状态 0初始化 1审核通过 2审核不通过
	 * 默认值为0 
	 */
	@Column(nullable = false, length = 2)
	@ColumnDefault("0")
	@Builder.Default
	private Integer state = 0;
	/**
	 * 创建时间
	 */
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;
	/**
	 * 修改时间
	 */
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@LastModifiedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedAt;

	@Transient
	@Builder.Default
	private Integer page = 1;
	@Transient
	private Integer pageSize;
	@Transient
	@Builder.Default
	private Integer sortFlag = 0;
	@Transient
	@Builder.Default
	private String sortParam = "updatedAt";

	@Transient
	private LocalDateTime createdTimeStart;
	@Transient
	private LocalDateTime createdTimeEnd;
}
