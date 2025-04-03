package com.hsbc.pws.risk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hsbc.pws.risk.entity.RiskAssessmentHistory;


@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface RiskAssessmentHistoryDao extends JpaRepository<RiskAssessmentHistory, Integer>, JpaSpecificationExecutor<RiskAssessmentHistory> {
}