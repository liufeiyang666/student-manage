package com.example.demo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CourseVO {

    private Long id;

    private String courseName;

    private String courseCode;

    private BigDecimal credit;

    private Long teacherId;

    private String teacherName;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
