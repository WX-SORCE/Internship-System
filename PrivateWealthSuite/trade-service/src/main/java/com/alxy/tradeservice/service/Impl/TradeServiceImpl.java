package com.alxy.tradeservice.service.Impl;

import com.alxy.tradeservice.entiy.Log;
import com.alxy.tradeservice.entiy.Trade;
import com.alxy.tradeservice.repository.LogRepository;
import com.alxy.tradeservice.repository.TradeRepository;
import com.alxy.tradeservice.service.TradeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author 20260
 */
@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private LogRepository logRepository;
    /**
     * 创建新的交易记录，并将交易状态设置为 "待审批"，同时记录创建日志
     * 此方法使用 @Transactional 注解，确保操作的原子性，要么全部成功，要么全部失败
     * @param trade 传入的交易对象，包含交易的基本信息
     * @return 返回保存到数据库后的交易对象
     */
    @Transactional
    public Trade createTrade(Trade trade) {
        // 设置交易的创建时间为当前时间
        trade.setCreateTime(LocalDateTime.now());
        // 设置交易的更新时间为当前时间
        trade.setUpdateTime(LocalDateTime.now());
        // 设置交易的初始状态为 "待审批"
        trade.setStatus("待审批");
        // 调用 tradeRepository 的 save 方法将交易对象保存到数据库
        // 并将保存后的交易对象赋值给 savedTrade 变量
        Trade savedTrade = tradeRepository.save(trade);
        // 调用 writeLog 方法记录创建交易的日志信息
        writeLog(savedTrade.getTradeId(), "创建交易，状态：待审批");
        // 返回保存后的交易对象
        return savedTrade;
    }

    /**
     * 变更指定交易的状态，并记录状态变更日志
     * 此方法使用 @Transactional 注解，确保操作的原子性
     * @param tradeId 要变更状态的交易的 ID
     * @param newStatus 新的交易状态
     */
    @Transactional
    public int changeTradeStatus(String tradeId, String newStatus) {
        // 通过 tradeRepository 的 findById 方法根据交易 ID 查找交易对象
        // 如果找到则返回该对象，否则返回 null
        Trade trade = tradeRepository.findById(tradeId).orElse(null);
        // 检查交易对象是否存在
        if (trade != null) {
            // 如果交易对象存在，将其状态设置为新的状态
            trade.setStatus(newStatus);
            // 更新交易的更新时间为当前时间
            trade.setUpdateTime(LocalDateTime.now());
            // 调用 tradeRepository 的 save 方法将更新后的交易对象保存到数据库
            tradeRepository.save(trade);
            // 调用 writeLog 方法记录交易状态变更的日志信息
            writeLog(tradeId, "变更交易状态为：" + newStatus);
            return 1;
        }
        return 0;
    }

    /**
     * 记录交易相关的日志信息
     * 该方法为私有方法，仅供内部调用
     * @param tradeId 关联的交易 ID
     * @param logMessage 日志消息内容
     */
    private void writeLog(String tradeId, String logMessage) {
        // 创建一个新的 Log 对象
        Log log = new Log();
        // 设置日志关联的交易 ID
        log.setTradeId(tradeId);
        // 设置日志的消息内容
        log.setLogMessage(logMessage);
        // 设置日志的记录时间为当前时间
        log.setLogTime(new Date());
        // 调用 logRepository 的 save 方法将日志对象保存到数据库
        logRepository.save(log);
    }

    /**
     * 执行指定的交易，前提是交易状态为 "已通过"
     * 执行后将交易状态更新为 "已执行"，并记录执行日志
     * 此方法使用 @Transactional 注解，确保操作的原子性
     * @param tradeId 要执行的交易的 ID
     */
    @Transactional
    public int executeTrade(String tradeId) {
        // 通过 tradeRepository 的 findById 方法根据交易 ID 查找交易对象
        // 如果找到则返回该对象，否则返回 null
        Trade trade = tradeRepository.findById(tradeId).orElse(null);
        // 检查交易对象是否存在，并且交易状态是否为 "已通过"
        if (trade != null && "已通过".equals(trade.getStatus())) {
            // 如果条件满足，将交易状态设置为 "已执行"
            trade.setStatus("已执行");
            // 更新交易的更新时间为当前时间
            trade.setUpdateTime(LocalDateTime.now());
            // 调用 tradeRepository 的 save 方法将更新后的交易对象保存到数据库
            tradeRepository.save(trade);
            // 调用 writeLog 方法记录交易执行完成的日志信息
            writeLog(tradeId, "交易执行完成，状态：已执行");
            return 1;
        }
        trade.setStatus("已拒绝");
        writeLog(tradeId, "交易已拒绝，状态：已拒绝");
        return 0;
    }

    @Override
    public List<Trade> getTradesByClientId(String clientId) {
        return tradeRepository.findByClientId(clientId);
    }
}
