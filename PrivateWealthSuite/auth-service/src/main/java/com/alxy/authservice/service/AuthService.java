package com.alxy.authservice.service;

import com.alxy.authservice.dto.ClientDTO;
import com.alxy.authservice.dto.Request;
import com.alxy.authservice.dto.Result;

public interface AuthService {


    public Result<?> loginWithPassword(String phoneNumber, String password);

    public Result<?> loginWithOAuth2(String oauthCode, String provider);

    public Result<?> createUser(ClientDTO request);
}