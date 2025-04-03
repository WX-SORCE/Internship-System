package com.alxy.approvalservice.Service;

import com.alxy.approvalservice.Entity.Approval;
import com.alxy.approvalservice.Repository.ApprovalRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ApprovalService {

    @Resource
    ApprovalRepository approvalRepository;
    public Boolean addApprovalRecord(Approval approval) {
        approvalRepository.save(approval);
        return true;
    }

}
