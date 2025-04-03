package com.hsbc.pws.risk.repository;

import com.hsbc.pws.risk.entity.AssessmentInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface AssessmentInfoDao extends JpaRepository<AssessmentInfo, Integer>, JpaSpecificationExecutor<AssessmentInfo> {
	public List<AssessmentInfo> findByClientId(String clientId);
}