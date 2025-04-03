package com.alxy.tradeservice.service;

import com.alxy.tradeservice.entiy.Trade;

import java.util.List;

public interface TradeService {
    Trade createTrade(Trade trade);

    int changeTradeStatus(String tradeId, String newStatus);

    int executeTrade(String tradeId);

    List<Trade> getTradesByClientId(String clientId);

}
