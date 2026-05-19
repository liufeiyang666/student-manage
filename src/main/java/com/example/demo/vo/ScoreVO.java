package com.example.demo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ScoreVO {

    private Long id;

    private Long studentId;

    private String studentNo;

    private String studentName;

    private Long courseId;

    private String courseName;

    private String courseCode;

    private BigDecimal usualScore;

    private BigDecimal examScore;

    private BigDecimal totalScore;

    private String semester;

    private String className;

    private String teacherName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
