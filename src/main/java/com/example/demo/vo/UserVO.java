package com.example.demo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {

    private Long id;

    private String username;

    private String realName;

    private String email;

    private String phone;

    private Integer status;

    private String roleCode;

    private String roleName;

    private LocalDateTime createTime;
}
