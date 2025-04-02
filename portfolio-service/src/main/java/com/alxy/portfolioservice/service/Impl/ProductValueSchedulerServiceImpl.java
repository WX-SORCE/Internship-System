package com.alxy.portfolioservice.service.Impl;



import com.alxy.portfolioservice.entiy.ProductValueHistory;
import com.alxy.portfolioservice.repository.ProductValueHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class ProductValueSchedulerServiceImpl {

    @Autowired
    private ProductValueHistoryRepository repository;

    // 定义最大波动百分比，例如 5%
    private static final double MAX_FLUCTUATION_PERCENT = 0.05;

    // 更新当天记录的金额
    @Scheduled(fixedRate = 1000 * 20) // 每20秒执行一次
    public void updateTodayRecordAmount() {
        // 获取当天的开始和结束时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfDay = calendar.getTime();

        // 查询当天的记录
        List<ProductValueHistory> histories = repository.findByDateBetween(startOfDay, endOfDay);

        Random random = new Random();
        for (ProductValueHistory history : histories) {
            // 生成 -MAX_FLUCTUATION_PERCENT 到 MAX_FLUCTUATION_PERCENT 之间的随机波动百分比
            double fluctuation = (random.nextDouble() * 2 - 1) * MAX_FLUCTUATION_PERCENT;
            // 计算新的价格
            double newPrice = history.getValue() * (1 + fluctuation);
            // 确保价格不会为负数
            newPrice = Math.max(0, newPrice);
            // 保留两位小数
            BigDecimal newAmount = BigDecimal.valueOf(newPrice).setScale(2, RoundingMode.HALF_UP);
            history.setValue(newAmount.doubleValue());
            System.out.println("++++++++++++++" + history.getId() + " " + history.getValue() + "++++++++++++++");
        }

        // 保存更新后的记录
        repository.saveAll(histories);
    }
    // 每2分钟检测一下ProductValueHistory的所有itemId最近日期商品有当日的日期数据没，没有则添加一个按itemId的type给合适的价格
    @Scheduled(fixedRate = 1000 * 120) // 每2分钟执行一次
    public void checkAndAddTodayRecord() {
        // 获取当天日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();

        // 获取所有不同的 itemId
        List<String> itemIds = repository.findAllDistinctItemIds();

        for (String itemId : itemIds) {
            // 获取该 itemId 的最近记录
            ProductValueHistory latestRecord = repository.findLatestRecordByItemId(itemId);

            if (latestRecord == null ||!isSameDay(latestRecord.getDate(), today)) {
                // 如果没有最近记录或者最近记录不是今天，添加一条新记录
                String type = latestRecord != null ? latestRecord.getType() : null;
                double appropriatePrice = getAppropriatePrice(type);
                ProductValueHistory newRecord = new ProductValueHistory();
                newRecord.setItemId(itemId);
                newRecord.setType(type);
                newRecord.setDate(new Date());
                newRecord.setValue(appropriatePrice);
                repository.save(newRecord);
                System.out.println("++++++++++++++ 添加新记录: itemId=" + itemId + ", value=" + appropriatePrice + "++++++++++++++");
            }
        }
    }

    // 判断两个日期是否为同一天
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    // 根据 type 获取合适的价格，这里简单模拟，你可以根据实际情况修改
    private double getAppropriatePrice(String type) {
        Random random = new Random();
        if ("Stock".equals(type)) {
            return 50 + random.nextDouble() * 50; // 股票价格在 50 - 100 之间
        } else if ("Bond".equals(type)) {
            return 100 + random.nextDouble() * 20; // 债券价格在 100 - 120 之间
        }
        return 20 + random.nextDouble() * 30; // 默认价格在 20 - 50 之间
    }
}