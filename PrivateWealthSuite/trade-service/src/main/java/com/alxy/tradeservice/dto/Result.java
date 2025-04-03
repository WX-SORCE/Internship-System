package com.alxy.tradeservice.dto;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;      // 状态码
    private String msg;        // 提示信息
    private String token;      // 认证令牌（可选）
    private T data;            // 泛型数据（可选）

    // 私有构造方法，强制使用静态工厂方法创建实例
    private Result(Integer code, String msg, String token, T data) {
        this.code = code;
        this.msg = msg;
        this.token = token;
        this.data = data;
    }

    // 成功：通用操作成功，无 token
    public static <T> Result<T> success(T data) {
        return new Result<>(0, "操作成功", null, data);
    }


    // 成功：仅返回token
    public static Result<String> success(String token) {
        return new Result<>(0, "操作成功", token, null);
    }

    // 成功：带 token 的场景（如登录）
    public static <T> Result<T> success(String token, T data) {
        return new Result<>(0, "登录成功", token, data);
    }

    // 成功：无数据和 token 的简单成功
    public static Result<Void> success() {
        return new Result<>(0, "操作成功", null, null);
    }

    // 失败：自定义消息
    public static Result<Void> error(String message) {
        return new Result<>(1, message, null, null);
    }

    // 失败：自定义状态码和消息
    public static Result<Void> error(Integer code, String message) {
        return new Result<>(code, message, null, null);
    }
}