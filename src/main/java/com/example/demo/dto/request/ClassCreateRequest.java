package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClassCreateRequest {

    @NotBlank(message = "班级名称不能为空")
    private String className;

    @NotBlank(message = "班级编号不能为空")
    private String classCode;

    private Long teacherId;

    private String description;
}
