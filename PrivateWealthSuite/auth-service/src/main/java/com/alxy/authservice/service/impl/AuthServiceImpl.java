package com.alxy.authservice.service.impl;

import com.alxy.authservice.controller.ClientServiceFeign;

import com.alxy.authservice.dto.ClientDTO;
import com.alxy.authservice.dto.Result;
import com.alxy.authservice.entity.Client;
import com.alxy.authservice.entity.User;
import com.alxy.authservice.repository.UserRepository;
import com.alxy.authservice.service.AuthService;
import com.alxy.authservice.utils.JwtUtil;
import com.alxy.authservice.utils.Md5Util;
import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private ClientServiceFeign clientServiceFeign;

    @Resource
    private KafkaTemplate<String,Object> kafkaTemplate;


    /**
     * 账号密码登录
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @return Result<String> 包含token的登录结果
     */
    @Override
    public Result<?> loginWithPassword(String phoneNumber, String password) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        boolean validPassword = Md5Util.checkPassword(password, user.getPasswordHash());
        return validPassword ? getToken(user) : Result.error("密码错误");
    }

    /**
     * OAuth2登录（待实现）
     *
     * @param oauthCode OAuth2授权码
     * @param provider  OAuth2提供商
     * @return Result<String> 包含token的登录结果
     */
    @Override
    public Result<?> loginWithOAuth2(String oauthCode, String provider) {
        // TODO: 实现OAuth2登录逻辑
        // 1. 验证oauthCode
        // 2. 获取用户信息
        // 3. 创建或关联用户
        // 4. 生成token
        return Result.error("功能尚未实现");
    }


    /**
     * 创建新用户
     *
     * @param clientDTO 注册请求
     * @return Result<Void> 创建结果
     */
    @Override
    public Result<?> createUser(ClientDTO clientDTO) {
        if ( userRepository.findByPhoneNumber(clientDTO.getPhoneNumber()) != null) {
            return Result.error("此手机号已被注册");
        }
        try {
            User user = new User();
            user.setUserId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12));
            user.setPhoneNumber(clientDTO.getPhoneNumber());
            user.setPasswordHash(Md5Util.getMD5String(clientDTO.getPassword()));
            user.setName(clientDTO.getName());
            user.setEmail(clientDTO.getEmail());
            user = userRepository.save(user);
            if(user.getIdentityLevel() == 1){
                Client client = new Client();
                client.setUserId(user.getUserId());
                client.setName(user.getName());
                client.setPhoneNumber(user.getPhoneNumber());
                client.setEmail(user.getEmail());
                client.setBirthday(clientDTO.getBirthday());
                client.setGender(clientDTO.getGender());
                client.setNationality(clientDTO.getNationality());
                client.setIdNumber(clientDTO.getIdNumber());
                client.setTotalAssets(clientDTO.getTotalAssets());
                client.setAdvisorId(clientDTO.getAdvisorId());
                clientServiceFeign.createClient(client);
            }
            return Result.success();
        } catch (Exception e) {
            return Result.error("用户创建失败");
        }
    }

    /**
     * 生成JWT token
     *
     * @param user 用户信息
     * @return Result<String> 包含token的结果
     */
    private Result<?> getToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());
        claims.put("identityLevel", user.getIdentityLevel());
        String token = JwtUtil.genToken(claims);
        return Result.success(token);
    }
}