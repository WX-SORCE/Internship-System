package com.alxy.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class ClientDTO {
    private String advisorId;

    private String name;

    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String nationality;

    private String idNumber;

    private String gender;

    private String email;

    private LocalDate birthday;

    private BigDecimal totalAssets;

}