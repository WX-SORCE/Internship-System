package com.alxy.approvalservice.Repository;

import com.alxy.approvalservice.Entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, String> {

    Approval findApprovalByTradeId(String threadId);

    List<Approval> findApprovalByLevel(Integer level);

    Approval findApprovalByApprovalId(String approvalId);
}