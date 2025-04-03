package com.alxy.portfolioservice.repository;


import com.alxy.portfolioservice.entiy.PortfolioItems;
import com.alxy.portfolioservice.entiy.Portfolios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfoliosRepository extends JpaRepository<Portfolios, Integer> {
    List<Portfolios> findByClientId(String clientId);

}

