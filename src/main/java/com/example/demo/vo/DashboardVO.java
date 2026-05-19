package com.example.demo.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DashboardVO {

    private Long studentCount;

    private Long teacherCount;

    private Long classCount;

    private Long courseCount;

    private List<StudentVO> recentStudents;

    private List<Map<String, Object>> scoreDistribution;

    private List<Map<String, Object>> classStatistics;
}
