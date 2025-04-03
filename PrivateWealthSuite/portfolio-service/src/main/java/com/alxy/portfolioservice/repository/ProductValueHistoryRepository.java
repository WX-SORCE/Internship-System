package com.alxy.portfolioservice.repository;

import com.alxy.portfolioservice.entiy.ProductValueHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ProductValueHistoryRepository extends JpaRepository<ProductValueHistory, Long> {
    List<ProductValueHistory> findByItemId(String itemId);
    // 查询当天的记录
    List<ProductValueHistory> findByDateBetween(Date startOfDay, Date endOfDay);

    @Query("SELECT pvh FROM ProductValueHistory pvh WHERE pvh.itemId IN :itemIds AND pvh.date >= :startDate")
    List<ProductValueHistory> findByItemIdsAndDateAfter(List<String> itemIds, Date startDate);

    List<ProductValueHistory> getProductValueHistoriesByItemId(String e);

    @Query("SELECT pvh FROM ProductValueHistory pvh WHERE pvh.itemId IN :itemIds AND pvh.date BETWEEN :startDate AND :endDate")
    List<ProductValueHistory> findByItemIdsAndDateBetween(@Param("itemIds") List<String> itemIds, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    // 获取该 itemId 的最近记录
    @Query("SELECT pvh FROM ProductValueHistory pvh WHERE pvh.itemId = :itemId ORDER BY pvh.date DESC LIMIT 1")
    ProductValueHistory findLatestRecordByItemId(String itemId);

    @Query("SELECT pvh.date, AVG(pvh.value) FROM ProductValueHistory pvh " +
            "WHERE pvh.itemId IN :itemIds AND pvh.date BETWEEN :startDate AND :endDate " +
            "GROUP BY pvh.date ORDER BY pvh.date")
    List<Object[]> findAverageValueByItemIdsAndDateRange(@Param("itemIds") List<String> itemIds,
                                                         @Param("startDate") Date startDate,
                                                         @Param("endDate") Date endDate);

    @Query("SELECT DISTINCT pvh.itemId FROM ProductValueHistory pvh")
    List<String> findAllDistinctItemIds();

    // 根据 item_id 按日期降序排序获取记录
    @Query("SELECT pvh FROM ProductValueHistory pvh WHERE pvh.itemId = :itemId ORDER BY pvh.date DESC")
    List<ProductValueHistory> findByItemIdOrderByDateDesc(String itemId);


}