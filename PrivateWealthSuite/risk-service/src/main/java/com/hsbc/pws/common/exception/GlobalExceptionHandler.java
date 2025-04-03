package com.hsbc.pws.common.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hsbc.pws.common.response.Result;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;


@ControllerAdvice
@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
	private final HttpServletRequest request;

	public GlobalExceptionHandler(HttpServletRequest request) {
		this.request = request;
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Result<?> httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e) {
		log.error("请求地址'{}'不支持'{}'Content-Type！", this.request.getRequestURI(), e.getContentType());
		return Result.error("请求Content-Type不支持！");
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("请求地址'{}'不支持'{}'方法！", this.request.getRequestURI(), e.getMethod());
		return Result.error("请求方法不支持！");
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Result<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error("请求地址'{}'请求体错误！{}", this.request.getRequestURI(), e.getMessage());
		return Result.error("请求体错误！");
	}

	@ExceptionHandler(NullPointerException.class)
	public Result<?> nullPointerExceptionHandler(NullPointerException e) {
		log.error(e.getMessage(), e);
		return Result.error("系统空指针错误！");
	}

	@ExceptionHandler(RuntimeException.class)
	public Result<?> handleRuntimeException(RuntimeException e) {
		log.error(e.getMessage(), e);
		return Result.error("系统运行错误！");
	}

	@ExceptionHandler(ServiceException.class)
	public Result<?> handleServiceException(ServiceException e) {
		log.error(e.getMessage(), e);
		return Result.error("系统服务错误！");
	}

	@ExceptionHandler(Exception.class)
	public Result<?> handleException(Exception e) {
		log.error(e.getMessage(), e);
		return Result.error("系统句柄错误！");
	}

	// 处理@RequestBody参数校验
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<String> errorMsg = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

		log.error("请求地址'{}'请求参数校验错误！{}", this.request.getRequestURI(), errorMsg);

		return Result.error(StringUtils.collectionToCommaDelimitedString(errorMsg));
    }

	// 处理application/x-www-form-urlencoded参数校验
	@ExceptionHandler(BindException.class)
	public Result<?> bindExceptionHandler(BindException e) {
		String errorMsg = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());

		log.error("请求地址'{}'请求参数校验错误！{}", this.request.getRequestURI(), errorMsg);
		
		return Result.error(errorMsg);
	}

	// 处理@RequestParam �? @PathVariable参数校验
	@ExceptionHandler(ConstraintViolationException.class)
	public Result<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
		String errorMsg = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
		
		log.error("请求地址'{}'请求参数校验错误！{}", this.request.getRequestURI(), errorMsg);

		return Result.error(errorMsg);
	}
}