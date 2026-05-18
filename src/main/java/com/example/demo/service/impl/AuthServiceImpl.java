package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.entity.SysUser;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final SysUserMapper sysUserMapper;

    @Override
    public String login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(request.getUsername());

            SysUser user = sysUserMapper.selectByUsername(request.getUsername());
            if (user == null) {
                throw new BusinessException("用户不存在");
            }

            return token;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("登录失败: {}", e.getMessage());
            throw new BusinessException("用户名或密码错误");
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
