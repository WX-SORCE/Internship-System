package com.hsbc.pws;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories
@EntityScan
@EnableJpaAuditing
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@EnableCaching
@Slf4j
@EnableDiscoveryClient
@RefreshScope
@EnableFeignClients
public class RiskServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskServerApplication.class, args);
		log.info("客户风险评估服务启动成功.");
	}

	@PostConstruct
	public static void init() {
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteMapNullValue.getMask();
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteNullNumberAsZero.getMask();
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteNullListAsEmpty.getMask();
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteNullStringAsEmpty.getMask();
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteNullBooleanAsFalse.getMask();
		JSON.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
		log.info("系统fastjson 1 全局配置成功.");
	}
}
