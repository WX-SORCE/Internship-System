package com.alxy.advisoryservice.repository;

import com.alxy.advisoryservice.entity.ProductValueHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductValueHistoryRepository extends JpaRepository<ProductValueHistory, String> {
    
    List<ProductValueHistory> findByItemId(String itemId);
    
    List<ProductValueHistory> findByDateBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT p FROM ProductValueHistory p WHERE p.itemId IN :itemIds ORDER BY p.date DESC")
    List<ProductValueHistory> findLatestByItemIds(@Param("itemIds") List<String> itemIds);
}

