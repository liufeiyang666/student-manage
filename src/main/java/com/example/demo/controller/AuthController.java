package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.entity.SysUser;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "认证接口", description = "登录、退出、获取用户信息")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "登录", description = "用户名密码登录")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        SysUser user = userService.getCurrentUser();
        
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("tokenType", "Bearer");
        data.put("user", user);
        
        return Result.success(data);
    }

    @PostMapping("/logout")
    @Operation(summary = "退出登录", description = "清除登录状态")
    public Result<?> logout() {
        authService.logout();
        return Result.success("退出成功");
    }

    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的详细信息")
    public Result<SysUser> getCurrentUser() {
        SysUser user = userService.getCurrentUser();
        return Result.success(user);
    }
}
