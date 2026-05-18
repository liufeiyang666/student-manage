package com.example.demo.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentUpdateRequest {

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
