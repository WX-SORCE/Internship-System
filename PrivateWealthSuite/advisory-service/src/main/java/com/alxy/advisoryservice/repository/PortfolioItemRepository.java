package com.alxy.advisoryservice.repository;

import com.alxy.advisoryservice.dto.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, String> {
    
    List<PortfolioItem> findByPortfolioId(String portfolioId);
    
    List<PortfolioItem> findByProductCode(String productCode);
}