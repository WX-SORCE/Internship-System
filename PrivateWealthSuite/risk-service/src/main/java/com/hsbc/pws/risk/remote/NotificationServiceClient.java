package com.hsbc.pws.risk.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;


/**
@FeignClient(name = "notification")
public interface NotificationServiceClient {
**/
@Service
@Slf4j
public class NotificationServiceClient {
	public int nextKyc(String clientId) {
		log.info("调用通知服务接口...【模拟】");
		log.info("请求参数是:clientId{}", clientId);
		log.info("调用通知服务接口成功.【模拟】");
		return 200;
	}

/**
	@PostMapping("/nextKyc")
	public int nextKyc(@RequestParam String clientId);
**/
}
