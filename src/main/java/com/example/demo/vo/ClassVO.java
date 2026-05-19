package com.example.demo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassVO {

    private Long id;

    private String className;

    private String classCode;

    private Long teacherId;

    private String teacherName;

    private String description;

    private Integer studentCount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
