package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentCreateRequest {

    @NotBlank(message = "学号不能为空")
    private String studentNo;

    @NotBlank(message = "姓名不能为空")
    private String name;

    private String gender;

    private Integer age;

    private String phone;

    private String email;

    private Long classId;

    private LocalDate enrollmentDate;

    private String address;

    private Integer status;
}
