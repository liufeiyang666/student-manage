package com.example.demo.vo;

import lombok.Data;

@Data
public class LoginVO {

    private String token;

    private String tokenType;

    private Long expiresIn;

    private UserVO user;

    public LoginVO(String token, Long expiresIn, UserVO user) {
        this.token = token;
        this.tokenType = "Bearer";
        this.expiresIn = expiresIn;
        this.user = user;
    }
}
