package com.alxy.approvalservice.Controller;

import com.alxy.approvalservice.Dto.Client;
import com.alxy.approvalservice.Dto.Result;
import com.alxy.approvalservice.Dto.Trade;
import com.alxy.approvalservice.Dto.User;
import com.alxy.approvalservice.Entity.Approval;
import com.alxy.approvalservice.Repository.ApprovalRepository;
import com.alxy.approvalservice.Service.ApprovalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/approval")
public class ApprovalController {

    @Resource
    private ApprovalService approvalService;
    @Resource
    private AuthControllerFeign authControllerFeign;
    @Resource
    private TradeControllerFeign tradeControllerFeign;
    @Resource
    private ApprovalRepository approvalRepository;
    @Resource
    private ClientControllerFeign clientControllerFeign;

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;



    // 客户经理查自己审批的列表
    @GetMapping("/getApprovalList")
    public Result<?> getApprovalList(@RequestParam String userId) {
        try {
            // 1. 参数校验
            if (StringUtils.isBlank(userId)) {
                return Result.error("参数错误: userId不能为空");
            }
            // 2. 调用Feign客户端
            Result<?> userResult = authControllerFeign.getUser(userId);
            if (userResult == null) {
                return Result.error("调用用户服务失败: 无响应");
            }
            if (!(userResult.getCode() == 0)) {
                return Result.error(userResult.getMsg());
            }
            // 3. 数据处理
            User user = (User) userResult.getData();
            if (user == null) {
                return Result.error("用户不存在");
            }
            // 4. 查询审批列表
            List<Approval> approvalList = approvalRepository.findApprovalByLevel(user.getIdentityLevel());
            return Result.success(approvalList);
        } catch (Exception e) {
            return Result.error("系统繁忙，请稍后重试");
        }
    }


    // 客户获取自己的审批列表
    @GetMapping("/getApprovalsByClientId")
    public Result<?> getApprovalsByClientId(@RequestParam String clientId) {
        List<Trade> trades = tradeControllerFeign.getTradesByClientId(clientId).getData();
        if (trades == null || trades.isEmpty()) {
            return Result.success(Collections.emptyList());
        }
        List<Approval> approvalList = trades.stream()
                .map(trade -> approvalRepository.findApprovalByTradeId(trade.getTradeId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return Result.success(approvalList);
    }



    @PostMapping("/create")
    public Result<?> create(@RequestParam String treadId, @RequestParam String advisorId){
        Approval approval = new Approval();
        approval.setTradeId(treadId);
        approval.setApproverId(advisorId);
        approval.setLevel(2);
        if(approvalService.addApprovalRecord(approval)){
            return Result.success();
        }
        return Result.error("失败");
    }

    // 审批
    @PostMapping("/approval")
    public Result<?> approval(@RequestParam String approvalId, @RequestParam String userId) throws JsonProcessingException {
        User user = authControllerFeign.getUser(userId).getData();
        Integer identityLevel = user.getIdentityLevel();
        Approval approval = approvalRepository.findApprovalByApprovalId(approvalId);
        //clientID
        Trade trade = tradeControllerFeign.getByTradeId(approval.getTradeId()).getData();
        if(Objects.equals(identityLevel, approval.getLevel())){
            // 创建通知体
            Map<String, Object> event = new HashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();
            event.put("clientId",trade.getClientId() );
            event.put("tradeId",approval.getTradeId());
            // 通知审核结果
            if(approval.getLevel() == 2){
                // 客户经理审批通过
                event.put("decision","客户经理审批通过");
                event.put("comment", "无意见");
                approval.setApproverId(userId);
                approval.setLevel(approval.getLevel()+1);
                approval.setDecision("审核中");
                approvalRepository.save(approval);
                String message = objectMapper.writeValueAsString(event);
                kafkaTemplate.send("approval-notifications", message);
            }else if(approval.getLevel() == 3){
                // 风控人员审批通过
                event.put("decision","风控人员审批通过");
                event.put("comment", "无意见");
                approval.setApproverId(userId);
                approval.setLevel(approval.getLevel()+1);
                approvalRepository.save(approval);
                String message = objectMapper.writeValueAsString(event);
                kafkaTemplate.send("approval-notifications", message);
            }else if(approval.getLevel() == 4){
                // 合规人员审批通过
                // 1、状态修改为通过
                approval.setDecision("通过");
                approvalRepository.save(approval);
                // 2、通知修改状态
                event.put("decision","合规人员审批通过");
                event.put("comment", "无意见");
                approval.setApproverId(userId);
                approval.setLevel(approval.getLevel()+1);
                approvalRepository.save(approval);
                String message = objectMapper.writeValueAsString(event);
                kafkaTemplate.send("approval-notifications", message);
                // 3、修改订单状态
                tradeControllerFeign.changeTradeStatus("已执行",approval.getTradeId());
            }else{
                // 拒绝
                event.put("decision","审批失败");
                event.put("comment", "有意见");
                String message = objectMapper.writeValueAsString(event);
                kafkaTemplate.send("approval-notifications", message);
                tradeControllerFeign.changeTradeStatus("已拒绝",approval.getTradeId());
            }
            return Result.success();
        }
        return Result.error("您无权通过！");
    }




    // 驳回
    @PostMapping("/resubmit")
    public Result<?> rejectAndSubmit(List<String> tradeList, String userId) throws JsonProcessingException {
        User user = authControllerFeign.getUser(userId).getData();
        for (String tradeId : tradeList) {
            Approval approval = approvalRepository.findApprovalByTradeId(tradeId);
            Integer identityLevel = user.getIdentityLevel();
            if(Objects.equals(identityLevel, approval.getLevel())){
                // 1、状态修改为拒绝
                approval.setDecision("拒绝");
                approval.setLevel(0);
                approvalRepository.save(approval);
                // 2、通知修改状态
//                Map<String, Object> event = new HashMap<>();
//                ObjectMapper objectMapper = new ObjectMapper();
//                event.put("clientId",);
//                event.put("tradeId",approval.getTradeId());
//                event.put("decision","已拒绝");
//                event.put("comment", "您的信息不符合我司标准");
//                String message = objectMapper.writeValueAsString(event);
//                kafkaTemplate.send("approval-notifications", message);
//                approvalStatusChange.setOldStatus("审核中");
//                approvalStatusChange.setNewStatus("已拒绝");
//                kafkaTemplate.send("trade-notifications", approvalStatusChange);
                // 3、修改订单状态
                tradeControllerFeign.changeTradeStatus("已拒绝",tradeId);
            }
        }
        return Result.success();
    }


//    // 获取交易审批历史
//    @PostMapping("/history")
//    public Result<?> getApprovalHistory(String userId){
//        List<Approval> approvalList = approvalRepository.findApprovalByApprovalId(userId);
//        return Result.success(approvalList);
//    }

}
