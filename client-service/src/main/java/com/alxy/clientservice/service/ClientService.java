package com.alxy.clientservice.service;

import com.alxy.clientservice.dto.CustomerRisk;
import com.alxy.clientservice.dto.UpdateInfo;
import com.alxy.clientservice.entiy.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ClientService {
    // 创建客户
    void createClient(Client client);

    Boolean updateClient(UpdateInfo updateInfo);

    // 根据ID获取客户
    Client getClientById(String clientId);

    //模糊查询
    Page<Client> searchClientsByNameAndPhone(String name, String phoneNumber, Pageable pageable);

    // 切换客户状态
    Client changeClientStatus(String clientId, String status);

    Client pay(String clientId, BigDecimal total);

    List<Client> getClientByAdvisorId(String advisorId);

    Client getClientByUserId(String userId);

    List<Client> findByKycDueDateBefore(LocalDate date);

    Integer updateStatusById(String clientId, String status);

    void updateRiskInfo(CustomerRisk customerRisk);
}
