package com.hsbc.pws.risk.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
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
public class RiskAssessmentHistory implements Serializable {
	private static final long serialVersionUID = -4780934737836037161L;
	
	/**
	 * 自增ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer id;
	/**
	 * 风险评估编号
	 */
	@Column(nullable = false, length = 32)
	private String riskId;
	/**
	 * 客户编号
	 */
	@Column(nullable = false, length = 18)
	private String clientId;
	/**
	 * 风控人员编号
	 */
	@Column(nullable = false, length = 12)
	private String evaluatorId;
	/**
	 * 风险得分
	 */
	@Column(nullable = false)
	@ColumnDefault("0")
	@Builder.Default
	private Integer score = 0;
	/**
	 * 风控等级
	 * 保守型 / 稳健型 / 进取型
	 */
	@Column(nullable = false, length = 20)
	private String resultLevel;
	/**
	 * 风险评估创建时间
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
	/**
	 * 记录创建时间
	 */
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

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
}
