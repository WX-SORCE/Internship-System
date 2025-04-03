package com.alxy.clientservice.repository;

import com.alxy.clientservice.dto.Result;
import com.alxy.clientservice.dto.UpdateInfo;
import com.alxy.clientservice.entiy.Client;
import feign.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {


    Client findByClientId(String integer);

    @Query("SELECT c FROM Client c WHERE " +
            "(:name IS NULL OR c.name LIKE %:name%) AND " +
            "(:phoneNumber IS NULL OR c.phoneNumber LIKE %:phoneNumber%)")
    Page<Client> searchClientsByNameAndPhone(String name, String phoneNumber, Pageable pageable);


    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.totalAssets = :totalAssets, c.riskLevel = :riskLevel, c.incomeLevel = :incomeLevel, c.kycDueDate = :kycDueDate WHERE c.clientId = :clientId")
    Boolean updateClient(BigDecimal totalAssets,Integer riskLevel, Integer incomeLevel, LocalDate kycDueDate, String clientId);


    List<Client> findClientsByAdvisorId(String advisorId);

    Client findByUserId(String userId);


    List<Client> findByKycDueDateBefore(LocalDate date);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.status = :status WHERE c.clientId = :clientId")
    Integer updateStatusByClientId(String clientId, String status);
}
