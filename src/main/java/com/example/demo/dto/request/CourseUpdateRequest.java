package com.example.demo.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseUpdateRequest {

    private String courseName;

    private String courseCode;

    private BigDecimal credit;

    private Long teacherId;

    private String description;
}
