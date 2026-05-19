package com.example.demo.dto.request;

import lombok.Data;

@Data
public class TeacherUpdateRequest {

    private String name;

    private String gender;

    private Integer age;

    private String phone;

    private String email;
}
