package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.vo.LoginVO;

public interface AuthService {

    LoginVO login(LoginRequest request);

    void logout();
}
