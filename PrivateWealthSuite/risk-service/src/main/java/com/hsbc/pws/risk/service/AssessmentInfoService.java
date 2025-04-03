package com.hsbc.pws.risk.service;

import com.hsbc.pws.risk.constant.GlobalConfig;
import com.hsbc.pws.risk.entity.AssessmentInfo;
import com.hsbc.pws.risk.pojo.AssessmentInfoCheck;
import com.hsbc.pws.risk.repository.AssessmentInfoDao;

import jakarta.annotation.Resource;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


@Service
@Slf4j
public class AssessmentInfoService {
	@Resource
	private AssessmentInfoDao assessmentInfoDao;

	@Resource
	private RiskAssessmentService riskAssessmentService;

	@Resource
	private GlobalConfig globalConfig;

	// 分页查询
	public Page<AssessmentInfo> list(AssessmentInfo request) throws Exception {
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
		Page<AssessmentInfo> objects = this.assessmentInfoDao.findAll(getSpecification(request), pageRequest);
		return objects;
	}

	// 保存
	public void save(AssessmentInfo assessmentInfo) throws Exception {
		this.assessmentInfoDao.save(assessmentInfo);
	}

	//审核
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void check(AssessmentInfoCheck assessmentInfoCheck) throws Exception {
		AssessmentInfo assessmentInfo = findById(assessmentInfoCheck.getAiId());
		if (!Objects.isNull(assessmentInfo)) {
			assessmentInfo.setState(1);
			this.assessmentInfoDao.save(assessmentInfo);

			this.riskAssessmentService.save(assessmentInfoCheck, calculateScore(assessmentInfo), assessmentInfo.getTotalAssets(), assessmentInfo.getMonthlyIncome());
		}
	}

	// Id查询
	public AssessmentInfo findById(Integer aiId) throws Exception {
		AssessmentInfo assessmentInfo = this.assessmentInfoDao.findById(aiId).orElse(null);
		if (!Objects.isNull(assessmentInfo)) {
			assessmentInfo.setPage(null);
			assessmentInfo.setPageSize(null);
			assessmentInfo.setSortFlag(null);
			assessmentInfo.setSortParam(null);
		}

		return assessmentInfo;
	}

	// clientId查询
	public List<AssessmentInfo> findByClientId(String clientId) throws Exception {
		List<AssessmentInfo> assessmentInfos = this.assessmentInfoDao.findByClientId(clientId);
		if (!CollectionUtils.isEmpty(assessmentInfos)) {
			assessmentInfos.forEach(o -> {
				o.setPage(null);
				o.setPageSize(null);
				o.setSortFlag(null);
				o.setSortParam(null);
			});
		}
		
		return assessmentInfos;
	}

	// 条件查询
	private Specification<AssessmentInfo> getSpecification(AssessmentInfo assessmentInfo) throws Exception {
		return new Specification<AssessmentInfo>() {
			private static final long serialVersionUID = -3442054640349972116L;

			/*
			 * Root是查询对象，CriteriaQuery（）是生成查询语句。CriteriaBuilder是进行给多条件赋值和设置like等条件的对象引用。
			 */
			@Override
			public Predicate toPredicate(Root<AssessmentInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();

				if (StringUtils.isNotBlank(assessmentInfo.getClientId())) {
					list.add(criteriaBuilder.like(root.get("clientId"), "%" + assessmentInfo.getClientId().trim() + "%"));
				}
				if (Objects.nonNull(assessmentInfo.getState())) {
					list.add(criteriaBuilder.equal(root.get("state"), assessmentInfo.getState()));
				} else {
					list.add(criteriaBuilder.greaterThan(root.get("state"), -1));
				}

				if (Objects.nonNull(assessmentInfo.getCreatedTimeStart())) {
					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), assessmentInfo.getCreatedTimeStart()));
				}
                if (Objects.nonNull(assessmentInfo.getCreatedTimeEnd())) {
					list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), assessmentInfo.getCreatedTimeEnd()));
				}

                Predicate[] predicate = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicate));
			}
		};
	}

	private int calculateScore(AssessmentInfo assessmentInfo) {
		int score = ThreadLocalRandom.current().nextInt(0, 101);
		
		int scoreByTotalAssets = calculateScoreByTotalAssets(assessmentInfo.getTotalAssets().intValue());
		int scoreByMonthlyIncome = calculateScoreByMonthlyIncome(assessmentInfo.getMonthlyIncome().intValue());
		if (scoreByTotalAssets > 0 && scoreByMonthlyIncome > 0) {
			score = Math.round(score*20/100  + scoreByTotalAssets*50/100 + scoreByMonthlyIncome*30/100);
		}

		if (score < 1) {
			score = ThreadLocalRandom.current().nextInt(0, 101);
		} else if (score > 100) {
			score = 100;
		}

		return score;
	}

	private int calculateScoreByTotalAssets(int totalAssets) {
		int score = 5;
		
		if (totalAssets >= 5000000) {
			score = 100;
		} else if (totalAssets >= 3000000 && totalAssets < 5000000) {
			score = 90;
		} else if (totalAssets >= 1000000 && totalAssets < 3000000) {
			score = 80;
		} else if (totalAssets >= 800000 && totalAssets < 1000000) {
			score = 60;
		} else if (totalAssets >= 500000 && totalAssets < 800000) {
			score = 50;
		} else if (totalAssets >= 300000 && totalAssets < 500000) {
			score = 40;
		} else if (totalAssets >= 100000 && totalAssets < 300000) {
			score = 30;
		} else if (totalAssets >= 50000 && totalAssets < 100000) {
			score = 20;
		} else if (totalAssets >= 10000 && totalAssets < 50000) {
			score = 10;
		}

		return score;
	}

	private int calculateScoreByMonthlyIncome(int monthlyIncome) {
		int score = 5;
		if (monthlyIncome >= 3000000) {
			score = 100;
		} else if (monthlyIncome >= 1000000 && monthlyIncome < 3000000) {
			score = 90;
		} else if (monthlyIncome >= 500000 && monthlyIncome < 1000000) {
			score = 80;
		} else if (monthlyIncome >= 100000 && monthlyIncome < 500000) {
			score = 60;
		} else if (monthlyIncome >= 50000 && monthlyIncome < 100000) {
			score = 50;
		} else if (monthlyIncome >= 20000 && monthlyIncome < 50000) {
			score = 40;
		} else if (monthlyIncome >= 10000 && monthlyIncome < 20000) {
			score = 30;
		} else if (monthlyIncome >= 6000 && monthlyIncome < 10000) {
			score = 20;
		} else if (monthlyIncome >= 3000 && monthlyIncome < 6000) {
			score = 10;
		}

		return score;
	}

	public List<AssessmentInfo> findAll() {
		return assessmentInfoDao.findAll();
	}
}
