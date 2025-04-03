package com.alxy.clientservice.controller;

import com.alxy.clientservice.dto.ClientDTO;
import com.alxy.clientservice.dto.CustomerRisk;
import com.alxy.clientservice.dto.Result;
import com.alxy.clientservice.dto.UpdateInfo;
import com.alxy.clientservice.entiy.Client;
import com.alxy.clientservice.service.ClientService;
import com.alxy.clientservice.service.ExportService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
/**
 * @description:
 * @author: 宋枝波
 * @date: 2025-04-02 15:55
 */
@RestController
@RequestMapping("/v1/client")
public class ClientController {
    @Resource
    private ClientService clientService;
    @Resource
    private ExportService exportService;

    // 创建客户
    @PostMapping
    public Result<?> createClient(@Valid @RequestBody Client client) {
        try {
            clientService.createClient(client);
            return Result.success("创建成功");
        } catch (Exception e) {
            return Result.error("创建失败");
        }
    }


    // 计算资产等级，均匀分布
    private static final BigDecimal MAX_ASSETS = new BigDecimal("100000000000"); // 1000亿
    private static final int TOTAL_RANKS = 10;
    public static int calculateAssetRank(BigDecimal totalAssets) {
        if (totalAssets == null || totalAssets.compareTo(BigDecimal.ZERO) < 0) {
            return 0;
        }
        BigDecimal step = MAX_ASSETS.divide(new BigDecimal(TOTAL_RANKS), 2, RoundingMode.HALF_UP);
        int rank = totalAssets.divide(step, 0, RoundingMode.DOWN).intValue() + 1;
        return Math.min(rank, TOTAL_RANKS);
    }

    @GetMapping("/getThreeInfo")
    public Result<?> getThreeInfo(@RequestParam String clientId){
        Client client = clientService.getClientById(clientId);
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setIncomeLevel(client.getIncomeLevel());
        clientDTO.setRiskLevel(client.getRiskLevel());
        clientDTO.setTotalAssets(calculateAssetRank(client.getTotalAssets()));
        return Result.success(clientDTO);
    }

    @PostMapping("/updateRiskInfo")
    public Result<?> updateRiskInfo(UpdateInfo updateInfo){
        clientService.updateClient(updateInfo);
        return Result.success();
    }

    @GetMapping("/getByUserId")
    public Result<Client> getByUserId(@RequestParam String userId){
        return Result.success(clientService.getClientByUserId(userId));
    }

    // 获取Kyc到期的客户
    @PostMapping("/findByKycDateBefore")
    public Result<List<Client>> findByKycDueDateBefore(@RequestParam LocalDate date){
        return Result.success(clientService.findByKycDueDateBefore(date));
    }

    // 根据ID搜客户
    @GetMapping("/getByClientId")
    public Result<Client> getClientById(@RequestParam String clientId) {
        Client client = clientService.getClientById(clientId);
        return Result.success(client);
    }

    // 根据名称或电话模糊查找
    @GetMapping("/search")
    public Result<?> searchClients(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String phoneNumber,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Client> clients = clientService.searchClientsByNameAndPhone(name, phoneNumber, pageable);
        return Result.success(clients);
    }


    @GetMapping("/getClientsByAdvisorId")
    public Result<?> getClientsByAdvisorId(@RequestParam String advisorId){
        List<Client> clients = clientService.getClientByAdvisorId(advisorId);
        return Result.success(clients);
    }

    // http://localhost:8081/v1/client/export?advisorId=advisor003&format=excel
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportClients(@RequestParam("advisorId") String advisorId,
                                                @RequestParam("format") String format) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String fileName;
        MediaType contentType;

        if ("csv".equalsIgnoreCase(format)) {
            exportService.exportClientsByAdvisorToCSV(outputStream, advisorId);
            fileName = "clients_by_advisor_" + advisorId + ".csv";
            contentType = MediaType.APPLICATION_OCTET_STREAM;
        } else if ("excel".equalsIgnoreCase(format)) {
            exportService.exportClientsByAdvisorToExcel(outputStream, advisorId);
            fileName = "clients_by_advisor_" + advisorId + ".xlsx";
            contentType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        byte[] data = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentLength(data.length);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }


    // 更改客户状态
    @PutMapping("/updateStatusById")
    public Result<?> updateStatusById(@RequestParam String clientId, @RequestParam String Status){
        clientService.updateStatusById(clientId, Status);
        return Result.success();
    }

    // 风险评估更改状态
    @PutMapping("/updateRiskInfo")
    public Result<?> updateRiskInfo(@RequestBody CustomerRisk customerRisk ){
        clientService.updateRiskInfo(customerRisk);
        return Result.success();
    }



    // 处理切换客户状态的 PUT 请求
    @PutMapping("/{clientId}/status")
    public Result<?> changeClientStatus(@PathVariable String clientId,
                                        @RequestParam String status) {
        Client client = clientService.changeClientStatus(clientId, status);
        if (client != null) {
            return Result.success(client);
        }
        return Result.error("客户未找到");
    }

    @PutMapping("/{clientId}/pay")
    public Result<?> pay(@PathVariable String clientId,
                         @RequestParam BigDecimal total){
        Client client = clientService.pay(clientId, total);
        if (client != null) {
            return Result.success(client);
        }
        return Result.success();
    }
}