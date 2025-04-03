package com.alxy.authservice.config;


import com.alxy.authservice.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 全局异常处理类
@RestControllerAdvice
public class GlobalHandler {

    // 处理校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 返回 400 状态码
    public Result<?> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("参数校验失败：");
        result.getAllErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        });
        return Result.error(errorMessage.toString());
    }

    // 处理其他异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 返回 500 状态码
    public Result<?> handleException(Exception ex) {
        return Result.error("服务器内部错误：" + ex.getMessage());
    }
}
