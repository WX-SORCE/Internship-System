package com.alxy.portfolioservice.repository;

import com.alxy.portfolioservice.entiy.PortfolioItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioItemsRepository extends JpaRepository<PortfolioItems, Integer> {

    List<PortfolioItems> findByPortfoliosId(String portfolioId);
}
