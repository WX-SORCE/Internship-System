package com.hsbc.pws.risk.remote;

import com.hsbc.pws.risk.controller.ClientServiceFeign;
import com.hsbc.pws.risk.controller.AuthServiceFeign;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.hsbc.pws.risk.pojo.CustomerRisk;

import lombok.extern.slf4j.Slf4j;


/**
@FeignClient(name = "client")
public interface CustomerServiceClient {
**/
@Service
@Slf4j
public class CustomerServiceClient {

	@Resource
	ClientServiceFeign clientServiceFeign;
	@Resource
	AuthServiceFeign authServiceFeign;


	public int saveRisk(CustomerRisk customerRisk) {
//		log.info("调用客户服务接口...【模拟】");
//		log.info("请求参数是:{}", JSON.toJSONString(customerRisk));
//		log.info("调用客户服务接口成功.【模拟】");
		// 修改user表
		authServiceFeign.updateLevel(customerRisk.getClientId(),1,"活跃");
		// 修改client表
		clientServiceFeign.updateRiskInfo(customerRisk);
		return 200;
	}

/**
	@PostMapping("/saveRisk")
	public int saveRisk(@RequestBody CustomerRisk customerRisk);
**/
}
