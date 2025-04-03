package com.alxy.advisoryservice.repository;

import com.alxy.advisoryservice.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, String> {
    
    List<Recommendation> findByClientId(String clientId);
    
    List<Recommendation> findByAdvisorIdAndCreatedAtBetween(String advisorId, Date start, Date end);

    List<Recommendation> findByClientIdOrderByCreatedAtDesc(String clientId);

    List<Recommendation> findRecommendationByClientId(String clientId);

    Recommendation findRecommendationByRecommendationId(String recommendationId);
}