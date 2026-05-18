package com.example.demo.dto.request;

import lombok.Data;

@Data
public class ClassUpdateRequest {

    private String className;

    private String classCode;

    private Long teacherId;

    private String description;
}
