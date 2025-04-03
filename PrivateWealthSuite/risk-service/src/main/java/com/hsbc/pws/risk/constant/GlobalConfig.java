package com.hsbc.pws.risk.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@RefreshScope
@Data
public class GlobalConfig {
	@Value("${rousing.page.size:10}")
	private int pageSize;

}
