package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseCreateRequest {

    @NotBlank(message = "课程名称不能为空")
    private String courseName;

    @NotBlank(message = "课程编号不能为空")
    private String courseCode;

    private BigDecimal credit;

    private Long teacherId;

    private String description;
}
