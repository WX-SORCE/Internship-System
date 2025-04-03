package com.alxy.authservice.controller;


import com.alxy.authservice.dto.ClientDTO;
import com.alxy.authservice.dto.Result;
import com.alxy.authservice.entity.Client;
import com.alxy.authservice.entity.User;
import com.alxy.authservice.repository.UserRepository;
import com.alxy.authservice.service.AuthService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@Validated
public class AuthController {
    @Resource
    private AuthService authService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private ClientServiceFeign clientServiceFeign;

    @PostMapping("/pwdLogin")
    public Result<?> loginWithPassword(@RequestBody ClientDTO clientDTO) {

        Result<?> result = authService.loginWithPassword(clientDTO.getPhoneNumber(), clientDTO.getPassword());
        if (result.getCode() == 0) {
            User user = userRepository.findByPhoneNumber(clientDTO.getPhoneNumber());
            Map<String, Object> data = new HashMap<>();
            data.put("phoneNumber", user.getPhoneNumber());
            data.put("userId",user.getUserId());
            if(user.getIdentityLevel() <= 1){
                Client client = clientServiceFeign.getByUserId(user.getUserId()).getData();
                data.put("clientId",client.getClientId());
            }
            data.put("identityLevel", user.getIdentityLevel());
            return Result.success(result.getToken(), data);
        }
        return Result.error("登录失败");
    }

    @GetMapping("/getUser")
    public Result<User> getUser(String userId){
        User user = userRepository.findUserByUserId(userId);
        return Result.success(user);
    }

    @PostMapping("/OAuth2Login")
    public Result<?> OAUth2Login() {
        return Result.success();
    }

    @PostMapping("/register")
    public Result<?> register(@Valid @RequestBody ClientDTO clientDTO) {
        try {
            Result<?> user= authService.createUser(clientDTO);
            if (user.getCode() == 0) {
                return Result.success("注册成功");
            }
            return Result.error("该账户已存在");
        } catch (IllegalArgumentException e) {
            return Result.error("格式错误");
        } catch (Exception e) {
            return Result.error("服务异常，请稍后重试");
        }
    }


    @PutMapping("/updateLevel")
    public Result<?> updateLevel(@RequestParam String clientId,@RequestParam Integer identityLevel ,@RequestParam String Status){
        userRepository.updateIdentityIdByUserId(clientId,identityLevel,Status);
        return Result.success();
    }

}