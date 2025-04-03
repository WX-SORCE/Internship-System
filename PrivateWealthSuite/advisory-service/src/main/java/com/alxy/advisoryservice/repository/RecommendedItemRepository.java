package com.alxy.advisoryservice.repository;

import com.alxy.advisoryservice.entity.RecommendedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendedItemRepository extends JpaRepository<RecommendedItem,String> {


    List<RecommendedItem> findRecommendedItemsByRecommendationId(String recommendId);
}
