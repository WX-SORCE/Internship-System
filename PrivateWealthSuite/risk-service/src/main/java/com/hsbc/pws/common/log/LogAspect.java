package com.hsbc.pws.common.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson2.JSON;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Aspect
@Service
@Slf4j
public class LogAspect {

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) "
			+ "|| @annotation(org.springframework.web.bind.annotation.PostMapping) "
			+ "|| @annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void pointCut() {
	}

	@Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long beginTime = System.currentTimeMillis();
		String clientIp = "127.0.0.1";
		String method = joinPoint.getSignature().getName();
		String clazz = joinPoint.getSignature().getDeclaringTypeName();

		try {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

			StringBuilder stringBuilder = new StringBuilder();
			if (attributes != null) {
				HttpServletRequest request = attributes.getRequest();
				
				clientIp = getIpAdrress(request);
				
				stringBuilder.append("headers{");
				Enumeration<?> headerNames = request.getHeaderNames();
				while (headerNames.hasMoreElements()) {
					String key = (String) headerNames.nextElement();
					String value = request.getHeader(key);
					stringBuilder.append(key);
					stringBuilder.append(" : ");
					if ("Authorization".equalsIgnoreCase(key) || "AccessToken".equalsIgnoreCase(key)) {
						stringBuilder.append("*******");
					} else {
						stringBuilder.append(value);
					}
					stringBuilder.append(",");
				}
				stringBuilder.append("}");

				log.debug("{}.{}:终端{}请求headers:{}", clazz, method, clientIp, stringBuilder);
			}

			stringBuilder = new StringBuilder();
			for (Object object : joinPoint.getArgs()) {
				if (object instanceof MultipartFile
						|| object instanceof FilePart
						|| object instanceof HttpServletRequest
						|| object instanceof HttpServletResponse
						|| object instanceof ServerHttpRequest
						|| object instanceof ServerHttpResponse
						|| object instanceof BeanPropertyBindingResult) {
					continue;
				}

				stringBuilder.append(JSON.toJSONString(object));
			}

			log.info("{}.{}:终端{}请求参数:{}", clazz, method, clientIp, stringBuilder);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		Object result = joinPoint.proceed();
		if (!(result instanceof Mono || result instanceof Flux)) {
			log.debug("{}.{}:终端{}响应耗时:{}毫秒", clazz, method, clientIp, System.currentTimeMillis() - beginTime);
			log.info("{}.{}:终端{}响应消息:{}", clazz, method, clientIp, result instanceof String ? result : JSON.toJSONString(result));
			return result;
		} else if (result instanceof Mono) {
			String ipAdrress = clientIp;
			return ((Mono) result).doOnSuccess(v -> {
				log.debug("{}.{}:终端{}响应耗时:{}毫秒", clazz, method, ipAdrress, System.currentTimeMillis() - beginTime);
				log.info("{}.{}:终端{}响应消息:{}", clazz, method, ipAdrress, result instanceof String ? result : JSON.toJSONString(result));
			});
		} else {
			String ipAdrress = clientIp;
			return ((Flux<?>) result).collectList().doOnSuccess(v -> {
				log.debug("{}.{}:终端{}响应耗时:{}毫秒", clazz, method, ipAdrress, System.currentTimeMillis() - beginTime);
				log.info("{}.{}:终端{}响应消息:{}", clazz, method, ipAdrress, result instanceof String ? result : JSON.toJSONString(result));
			});
		}
	}

	public String getIpAdrress(HttpServletRequest request) {
		String Xip = request.getHeader("X-Real-IP");
		String XFor = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = XFor.indexOf(",");
			if (index != -1) {
				return XFor.substring(0, index);
			} else {
				return XFor;
			}
		}
		XFor = Xip;
		if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
			return XFor;
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getRemoteAddr();
		}
		return XFor;
	}
}
