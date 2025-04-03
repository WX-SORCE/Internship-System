package com.alxy.clientservice.service.Impl;

import com.alxy.clientservice.dto.CustomerRisk;
import com.alxy.clientservice.dto.UpdateInfo;
import com.alxy.clientservice.entiy.Client;
import com.alxy.clientservice.repository.ClientRepository;
import com.alxy.clientservice.service.ClientService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
public class ClientServiceImpl implements ClientService {
    @Resource
    private ClientRepository clientRepository;

    // 创建客户
    @Override
    @Transactional
    public void createClient(Client client) {
        try {
            clientRepository.save(client);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public Boolean updateClient(UpdateInfo updateInfo){
    return clientRepository.updateClient(updateInfo.getTotal_assets(),
            updateInfo.getRiskLevel(),
            updateInfo.getIncomeLevel(),
            updateInfo.getKycDueDate(),
            updateInfo.getClientId());
    }


    // 根据ID获取客户
    @Override
    public Client getClientById(String clientId) {
        return clientRepository.findByClientId(clientId);
    }

    // 根据姓名模糊查询客户并分页
    @Override
    public Page<Client> searchClientsByNameAndPhone(String name, String phoneNumber, Pageable pageable) {
        return clientRepository.searchClientsByNameAndPhone(name, phoneNumber, pageable);
    }

    // 切换客户状态
    @Override
    public Client changeClientStatus(String clientId, String status) {
        Client client = clientRepository.findByClientId(clientId);
        client.setStatus(status);
        return clientRepository.save(client);
    }

    @Override
    public Client pay(String clientId, BigDecimal total) {
        Client client = clientRepository.findByClientId(clientId);
        client.setTotalAssets(total);
        clientRepository.save(client);
        return client;
    }

    @Override
    public List<Client> getClientByAdvisorId(String advisorId) {
        return clientRepository.findClientsByAdvisorId(advisorId);
    }

    @Override
    public Client getClientByUserId(String userId) {
        return clientRepository.findByUserId(userId);
    }

    @Override
    public List<Client> findByKycDueDateBefore(LocalDate date) {
        return clientRepository.findByKycDueDateBefore(date);
    }

    @Override
    public Integer updateStatusById(String clientId, String status) {
        return clientRepository.updateStatusByClientId(clientId, status);
    }

    @Override
    public void updateRiskInfo(CustomerRisk customerRisk) {
        Client client = new Client();
        client.setClientId(customerRisk.getClientId());
        client.setRiskLevel(customerRisk.getRiskLevel());
        // 用得分替代
        client.setIncomeLevel(customerRisk.getScore()/10);
        client.setKycDueDate(customerRisk.getNextKycDueDate());
        client.setTotalAssets(customerRisk.getTotalAssets());
        clientRepository.save(client);
    }
}
