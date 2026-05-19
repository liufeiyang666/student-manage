package com.example.demo.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentVO {

    private Long id;

    private String studentNo;

    private String name;

    private Integer gender;

    private String genderName;

    private Integer age;

    private String phone;

    private String email;

    private Long classId;

    private String className;

    private String classCode;

    private LocalDate enrollmentDate;

    private String address;

    private Integer status;

    private String statusName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
