package com.hsbc.pws.risk.controller;

import com.hsbc.pws.common.response.Result;
import com.hsbc.pws.risk.entity.AssessmentInfo;
import com.hsbc.pws.risk.pojo.AssessmentInfoCheck;
import com.hsbc.pws.risk.service.AssessmentInfoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/risk")
@ResponseBody
@Slf4j
public class AssessmentInfoController {
	@Resource
	private AssessmentInfoService assessmentInfoService;

	// 查询列表  -- 风控人员查询全部；   --
	@PostMapping("/info/list")
	public Result<Page<AssessmentInfo>> list(@RequestBody AssessmentInfo request) throws Exception {
		return Result.success(this.assessmentInfoService.list(request));
	}

	@PostMapping("/info/listAll")
	public Result<List<AssessmentInfo>> listAll(@RequestBody AssessmentInfo request){
		List<AssessmentInfo> assessmentInfos = assessmentInfoService.findAll();
		return Result.success(assessmentInfos);
	}

	// 保存 - 用户填写表单 -- 通知风控人员通过
	@PostMapping("/info/save")
	public Result<?> save(@Valid @RequestBody AssessmentInfo request)  throws Exception {
		if (request.getClientId().length() > 18) {
			log.warn("客户编号长度错误! ClientId:{}", request.getClientId());
			Result.error("客户编号长度错误!");
		}
		this.assessmentInfoService.save(request);
		return Result.success();
	}

	// 审核
	@PostMapping("/info/check")
	public Result<?> check(@Valid @RequestBody AssessmentInfoCheck assessmentInfoCheck) throws Exception {
		this.assessmentInfoService.check(assessmentInfoCheck);

		return Result.success();
	}

	// 查看
	@PostMapping("/info/viewById/{aiId}")
	public Result<AssessmentInfo> viewById(@PathVariable Integer aiId) throws Exception {
		AssessmentInfo assessmentInfo = this.assessmentInfoService.findById(aiId);
		if (assessmentInfo == null) {
			log.warn("评估表单数据不存在! aiId:{}", aiId);
			Result.error("评估表单数据不存在! aiId:" + aiId);
		}
		return Result.success(assessmentInfo);
	}

	// 查看
	@PostMapping("/info/viewByClientId/{clientId}")
	public Result<List<AssessmentInfo>> viewByClientId(@PathVariable String clientId) throws Exception {
		return Result.success(this.assessmentInfoService.findByClientId(clientId));
	}
}
