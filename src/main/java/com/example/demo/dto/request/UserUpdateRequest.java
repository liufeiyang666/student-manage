package com.example.demo.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequest {

    private String realName;

    private String email;

    private String phone;

    private String roleCode;
}
