package com.alxy.tradeservice.repository;


import com.alxy.tradeservice.entiy.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, String> {
    List<Trade> findByClientId(String clientId);

    Trade findByTradeId(String tradeId);
}