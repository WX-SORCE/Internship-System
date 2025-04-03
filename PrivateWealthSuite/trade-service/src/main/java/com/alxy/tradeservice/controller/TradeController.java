package com.alxy.tradeservice.controller;

import com.alxy.tradeservice.dto.Client;
import com.alxy.tradeservice.dto.Result;
import com.alxy.tradeservice.dto.TradeStatusChangeDTO;
import com.alxy.tradeservice.entiy.Trade;
import com.alxy.tradeservice.repository.TradeRepository;
import com.alxy.tradeservice.service.TradeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;



/**
 * @description:
 * @author: 宋枝波
 * @date: 2025-04-02 15:55
 */
@RestController
@RequestMapping("/v1/trade")
public class TradeController {

    // 自动注入 TradeService 类型的 Bean，用于调用业务逻辑层的方法
    @Resource
    private TradeService tradeService;

    @Resource
    private TradeRepository tradeRepository;

    @Resource
    private ApprovalFeign approvalFeign;
    @Resource
    private ClientFeign clientFeign;
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;


    // 提供clientId、productCode、amount即可
    @PostMapping
    public Result<?> createTrades(@RequestBody List<Trade> trades) {
        try {
            for (Trade trade : trades) {
                // 创建订单
                tradeService.createTrade(trade);
                // 创建审批服务
                approvalFeign.create(trade.getTradeId(),"");
                // 订单状态变更通知
                Map<String, Object> event = new HashMap<>();
                ObjectMapper objectMapper = new ObjectMapper();
                event.put("clientId", trade.getClientId());
                event.put("tradeId", trade.getTradeId());
                event.put("status", "订单创建成功！");

                String message = objectMapper.writeValueAsString(event);
                kafkaTemplate.send("trade-notifications", message);
            }
            return Result.success("订单创建成功");
        } catch (Exception e) {
            System.out.println("##########################" + e);
            return Result.error("订单生成失败");
        }
    }


    // 提供clientId、productCode、amount即可
    @PostMapping("/createTrade")
    public Result<?> createTrade(@RequestBody Trade trade) {
        try {
            tradeService.createTrade(trade);
            // 创建审批服务
            approvalFeign.create(trade.getTradeId(),"none");
            // 订单状态变更通知
            Map<String, Object> event = new HashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();
            event.put("clientId", trade.getClientId());
            event.put("tradeId", trade.getTradeId());
            event.put("status", "订单创建成功！");
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("trade-notifications", message);
            return Result.success("订单创建成功");
        } catch (Exception e) {
            return Result.error("订单生成失败");
        }
    }


    /**
     * 处理 HTTP PUT 请求，用于变更指定交易的状态
     * 请求路径格式为 /trades/{tradeId}/status
     *
     * @param tradeId   从路径中获取的交易 ID，用于指定要变更状态的交易
     * @param newStatus 从请求参数中获取的新的交易状态
     */
    @PutMapping("/{tradeId}/status")
    public Result<?> changeTradeStatus(
            @RequestParam String newStatus, @PathVariable String tradeId) throws JsonProcessingException {
        // 1、调用 TradeService 的 changeTradeStatus 方法来变更交易状态
        tradeService.changeTradeStatus(tradeId, newStatus);
        // 2、通过后进行支付
//        String oldStatus = null;
        if (Objects.equals(newStatus, "已执行")) {
            // 通过tread_id找到client_id
            Trade trade = tradeRepository.findByTradeId(tradeId);
//            oldStatus = trade.getStatus();
            // 通过client_id找到
            Client client = clientFeign.getClientById(trade.getClientId()).getData();
            client.setTotalAssets(client.getTotalAssets().subtract(trade.getAmount()));
            clientFeign.pay(client.getClientId(), client.getTotalAssets());
        }
        // 订单状态变更通知
        // TODO Kafka报错
        // 创建通知体
        Trade trade = tradeRepository.findByTradeId(tradeId);
        Map<String, Object> event = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        event.put("clientId", trade.getClientId());
        event.put("tradeId", tradeId);
        event.put("status", newStatus);

        String message = objectMapper.writeValueAsString(event);
        kafkaTemplate.send("trade-notifications", message);

        return Result.success();
    }

    /**
     * 处理 HTTP POST 请求，用于执行指定的交易
     * 请求路径格式为 /trades/{tradeId}/execute
     *
     * @param tradeId 从路径中获取的交易 ID，用于指定要执行的交易
     */
    @PostMapping("/{tradeId}/execute")
    public void executeTrade(@PathVariable String tradeId) {
        // 调用 TradeService 的 executeTrade 方法来执行交易
        tradeService.executeTrade(tradeId);
    }

    // 根据客户 ID 获取交易订单
    @GetMapping("/getTradesByClientId")
    public Result<List<Trade>> getTradesByClientId(@RequestParam String clientId) {
        List<Trade> trades = tradeService.getTradesByClientId(clientId);
        return Result.success(trades);
    }


    @GetMapping("/getClientIdByTradeId")
    public Result<Trade> getByTradeId(@RequestParam String tradeId){
        Trade trade = tradeRepository.findByTradeId(tradeId);
        return Result.success(trade);
    }
}