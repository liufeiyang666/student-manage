package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;

public interface AuthService {

    String login(LoginRequest request);

    void logout();
}
