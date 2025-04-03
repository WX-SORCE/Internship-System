package com.hsbc.pws.risk.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.hsbc.pws.risk.enums.RiskLevelEnum;
import com.hsbc.pws.risk.pojo.AssessmentInfoCheck;
import com.hsbc.pws.risk.pojo.CustomerRisk;
import com.hsbc.pws.risk.remote.CustomerServiceClient;
import com.hsbc.pws.risk.remote.NotificationServiceClient;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson2.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hsbc.pws.common.utils.LocalCacheUtils;
import com.hsbc.pws.risk.constant.GlobalConfig;
import com.hsbc.pws.risk.constant.GlobalConstant;
import com.hsbc.pws.risk.entity.RiskAssessment;
import com.hsbc.pws.risk.entity.RiskAssessmentHistory;
import com.hsbc.pws.risk.repository.RiskAssessmentDao;
import com.hsbc.pws.risk.repository.RiskAssessmentHistoryDao;

import jakarta.annotation.Resource;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class RiskAssessmentService {
	@Resource
	private RiskAssessmentDao riskAssessmentDao;
	@Resource
	private RiskAssessmentHistoryDao riskAssessmentHistoryDao;
	
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	@Resource
	private CustomerServiceClient customerServiceClient;
	@Resource
	private NotificationServiceClient notificationServiceClient;

	@Resource
	private GlobalConfig globalConfig;

	@Value("${risk.local.cache.expire:5}")
	private int cacheExpireTime;
	
	protected final Cache<String, List<RiskAssessment>> cache = Caffeine.newBuilder()
			.expireAfterWrite(Duration.ofMinutes(this.cacheExpireTime))
			.maximumSize(20000)
			.build();

	// 分页查询
	public Page<RiskAssessment> list(RiskAssessment request) throws Exception {
		Sort sort = Sort.by(Direction.ASC, request.getSortParam());
		if (request.getSortFlag() == 0) {
			sort = Sort.by(Direction.DESC, request.getSortParam());
			request.setSortFlag(1);
		} else {
			request.setSortFlag(0);
		}
		if (request.getPageSize() < 1) {
			request.setPageSize(this.globalConfig.getPageSize());
		}

		PageRequest pageRequest = PageRequest.of(request.getPage() - 1, request.getPageSize(), sort);
		Page<RiskAssessment> objects = this.riskAssessmentDao.findAll(getSpecification(request), pageRequest);
		return objects;
	}


	public List<RiskAssessment> listAll() throws Exception {
        return riskAssessmentDao.findAll();
	}



	// 初评、复评
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(AssessmentInfoCheck assessmentInfoCheck, int score, BigDecimal totalAssets, int monthlyIncome) throws Exception {
		String clientId = assessmentInfoCheck.getClientId();
		RiskAssessment riskAssessment = findByClientId(clientId);
		
		String riskId = null;
		if (!Objects.isNull(riskAssessment)) {
			riskId = riskAssessment.getRiskId();

			RiskAssessmentHistory riskAssessmentHistory = new RiskAssessmentHistory();
			BeanUtils.copyProperties(riskAssessmentHistory, riskAssessment);

			this.riskAssessmentHistoryDao.save(riskAssessmentHistory);
		}
		
		if (StringUtils.isBlank(riskId)) {
			riskAssessment = new RiskAssessment();
			BeanUtils.copyProperties(riskAssessment, assessmentInfoCheck);
			
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			String currentDate = LocalDate.now().format(dateTimeFormatter);
			riskId = "RISK-" + currentDate +  "-" + clientId;
			log.info("风险评估编号是:{}", riskId);
			riskAssessment.setRiskId(riskId);
		}

		riskAssessment.setEvaluatorId(assessmentInfoCheck.getEvaluatorId());
		riskAssessment.setRemarks(assessmentInfoCheck.getRemarks());
		riskAssessment.setScore(score);
		riskAssessment.setResultLevel(calculateRiskLevel(score));

		this.riskAssessmentDao.save(riskAssessment);
		//this.redisTemplate.opsForValue().set(GlobalConstant.REDIS_RISK_PREFIX + clientId, JSON.toJSONString(riskAssessment));
		
		//调用client服务修改riskLevel
		LocalDate nextKycDueDate = LocalDate.now().plusMonths(6);
		CustomerRisk customerRisk = new CustomerRisk();
		customerRisk.setClientId(clientId);
		customerRisk.setRiskLevel(riskAssessment.getResultLevel());
		customerRisk.setNextKycDueDate(nextKycDueDate);
		customerRisk.setTotalAssets(totalAssets);
		customerRisk.setIncomeLevel(calculateIncomeLevel(monthlyIncome));
		customerRisk.setScore(riskAssessment.getScore());
		int result = this.customerServiceClient.saveRisk(customerRisk);
		if (result != 200) {
			log.error("客户服务响应错误！请求参数是:{}", JSON.toJSONString(customerRisk));
		}
	}

	// riskId查询
	public RiskAssessment findById(String riskId) throws Exception {
		RiskAssessment riskAssessment = this.riskAssessmentDao.findById(riskId).orElse(null);
		if (!Objects.isNull(riskAssessment)) {
			riskAssessment.setPage(null);
			riskAssessment.setPageSize(null);
			riskAssessment.setSortFlag(null);
			riskAssessment.setSortParam(null);
		}

		return riskAssessment;
	}

	// clientId查询
	public RiskAssessment findByClientId(String clientId) throws Exception {
		RiskAssessment riskAssessment = null;
		List<RiskAssessment> riskAssessments = this.riskAssessmentDao.findByClientId(clientId);
		if (!CollectionUtils.isEmpty(riskAssessments)) {
			riskAssessment = riskAssessments.get(0);
			riskAssessment.setPage(null);
			riskAssessment.setPageSize(null);
			riskAssessment.setSortFlag(null);
			riskAssessment.setSortParam(null);
		}
		
		return riskAssessment;

		//TODO
		//获取缓存
		//return (RiskAssessment) LocalCacheUtils.get(GlobalConstant.REDIS_RISK_PREFIX + clientId);
	}

	// 条件查询
	private Specification<RiskAssessment> getSpecification(RiskAssessment riskAssessment) throws Exception {
		return new Specification<RiskAssessment>() {
			private static final long serialVersionUID = -3298982461324150876L;

			/*
			 * Root是查询对象，CriteriaQuery（）是生成查询语句。CriteriaBuilder是进行给多条件赋值和设置like等条件的对象引用。
			 */
			@Override
			public Predicate toPredicate(Root<RiskAssessment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();

				if (StringUtils.isNotBlank(riskAssessment.getClientId())) {
					list.add(criteriaBuilder.like(root.get("clientId"), "%" + riskAssessment.getClientId().trim() + "%"));
				}
				if (StringUtils.isNotBlank(riskAssessment.getEvaluatorId())) {
					list.add(criteriaBuilder.like(root.get("evaluatorId"), "%" + riskAssessment.getEvaluatorId().trim() + "%"));
				}
				if (Objects.nonNull(riskAssessment.getResultLevel())) {
					list.add(criteriaBuilder.equal(root.get("resultLevel"), riskAssessment.getResultLevel()));
				} else {
					list.add(criteriaBuilder.greaterThan(root.get("resultLevel"), -1));
				}

				if (Objects.nonNull(riskAssessment.getCreatedTimeStart())) {
					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), riskAssessment.getCreatedTimeStart()));
				}
                if (Objects.nonNull(riskAssessment.getCreatedTimeEnd())) {
					list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), riskAssessment.getCreatedTimeEnd()));
				}

                Predicate[] predicate = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicate));
			}
		};
	}

	@Scheduled(cron = "${risk.scheduled.cron.remind.HalfYear:0 0 8 * * ?}")
	public void changeVoiceHistory() {
		List<RiskAssessment> riskAssessments = this.riskAssessmentDao.findAllByHalfYear();
		if (CollectionUtils.isEmpty(riskAssessments)) {
			return;
		}
		
		riskAssessments.stream().forEach(o -> {
			String clientId = o.getClientId();
			log.info("风险评估编号是:{}; 客户编号是:{}", o.getRiskId(), clientId);
			
			int result = this.notificationServiceClient.nextKyc(clientId);
			if (result != 200) {
				log.error("客户服务响应错误！clientId:{}", clientId);
			}
		});
	}

	private String calculateRiskLevel(int score) {
		if (score >= 80 && score <= 100) {
			return RiskLevelEnum.AGGRESSIVE.getMsg(2);
		} else if (score >= 60 && score <= 80) {
			return RiskLevelEnum.AGGRESSIVE.getMsg(1);
		} else {
			return RiskLevelEnum.AGGRESSIVE.getMsg(0);
		}
	}

	private int calculateIncomeLevel(int monthlyIncome) {
		int incomeLevel = 0;
		if (monthlyIncome >= 3000000) {
			incomeLevel = 10;
		} else if (monthlyIncome >= 1000000 && monthlyIncome < 3000000) {
			incomeLevel = 9;
		} else if (monthlyIncome >= 500000 && monthlyIncome < 1000000) {
			incomeLevel = 8;
		} else if (monthlyIncome >= 100000 && monthlyIncome < 500000) {
			incomeLevel = 6;
		} else if (monthlyIncome >= 50000 && monthlyIncome < 100000) {
			incomeLevel = 5;
		} else if (monthlyIncome >= 20000 && monthlyIncome < 50000) {
			incomeLevel = 4;
		} else if (monthlyIncome >= 10000 && monthlyIncome < 20000) {
			incomeLevel = 3;
		} else if (monthlyIncome >= 6000 && monthlyIncome < 10000) {
			incomeLevel = 2;
		} else if (monthlyIncome >= 3000 && monthlyIncome < 6000) {
			incomeLevel = 1;
		}

		return incomeLevel;
	}
}
