package com.example.demo.controller;

import com.example.demo.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final SysUserMapper sysUserMapper;

    @GetMapping("/db")
    public Map<String, Object> testDb() {
        Map<String, Object> result = new HashMap<>();
        try {
            var user = sysUserMapper.selectByUsername("admin");
            if (user != null) {
                result.put("success", true);
                result.put("username", user.getUsername());
                result.put("passwordLength", user.getPassword().length());
            } else {
                result.put("success", false);
                result.put("message", "User not found");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
