package com.hsbc.pws.risk.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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


@Entity
@DynamicInsert
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RiskAssessment implements Serializable {
	private static final long serialVersionUID = -6282692539089156649L;
	
	/**
	 * 风险评估编号
	 */
	@Id
	@Column(nullable = false, length = 32)
	private String riskId;
	/**
	 * 客户编号
	 */
	@Column(nullable = false, length = 18)
	@NotBlank(message = "客户编号不能为空！")
	private String clientId;
	/**
	 * 风控人员编号
	 */
	@Column(nullable = false, length = 12)
	@NotBlank(message = "风控人员编号不能为空！")
	private String evaluatorId;
	/**
	 * 风险得分
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	@NotNull(message = "风险得分不能为空！")
	private Integer score = 0;
	/**
	 * 风控等级
	 * 保守型 / 稳健型 / 进取型
	 */
	@Column(nullable = false, length = 20)
	@NotBlank(message = "风控等级不能为空！")
	private String resultLevel;
	/**
	 * 创建时间
	 */
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;
	/**
	 * 附注说明
	 */
	@Column(columnDefinition = "TEXT")
	private String remarks;

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
	private String sortParam = "createdAt";
	
	@Transient
	private LocalDateTime createdTimeStart;
	@Transient
	private LocalDateTime createdTimeEnd;

}
