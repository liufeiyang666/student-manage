package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ScoreCreateRequest {

    @NotNull(message = "学生ID不能为空")
    private Long studentId;

    @NotNull(message = "课程ID不能为空")
    private Long courseId;

    private BigDecimal usualScore;

    private BigDecimal examScore;

    private String semester;
}
