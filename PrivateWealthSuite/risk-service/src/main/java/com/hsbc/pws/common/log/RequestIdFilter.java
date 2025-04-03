package com.hsbc.pws.common.log;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;


@Component
public class RequestIdFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			String requestId = httpServletRequest.getHeader("requestId");
			if (StringUtils.isBlank(requestId)) {
				MDC.put("traceId", UUID.randomUUID().toString().replace("-", ""));
			} else {
				MDC.put("traceId", requestId);
			}

			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			MDC.clear();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}
}
