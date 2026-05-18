package com.example.demo.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ScoreUpdateRequest {

    private BigDecimal usualScore;

    private BigDecimal examScore;

    private String semester;
}
