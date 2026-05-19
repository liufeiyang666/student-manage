package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeacherCreateRequest {

    @NotBlank(message = "教师编号不能为空")
    private String teacherNo;

    @NotBlank(message = "姓名不能为空")
    private String name;

    private String gender;

    private Integer age;

    private String phone;

    private String email;
}
