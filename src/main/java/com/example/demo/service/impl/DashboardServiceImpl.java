package com.example.demo.service.impl;

import com.example.demo.entity.ClassInfo;
import com.example.demo.mapper.ClassInfoMapper;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.service.DashboardService;
import com.example.demo.service.ScoreService;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final ClassInfoMapper classInfoMapper;
    private final CourseMapper courseMapper;
    private final StudentService studentService;
    private final ScoreService scoreService;

    @Override
    public Map<String, Object> getOverview() {
        Map<String, Object> vo = new HashMap<>();

        vo.put("studentCount", studentMapper.selectCount(null));
        vo.put("teacherCount", teacherMapper.selectCount(null));
        vo.put("classCount", classInfoMapper.selectCount(null));
        vo.put("courseCount", courseMapper.selectCount(null));

        vo.put("recentStudents", studentService.getRecentStudents(5));

        vo.put("scoreDistribution", scoreService.getScoreDistribution(null, null));

        vo.put("classStatistics", getClassStatistics());

        return vo;
    }

    private List<Map<String, Object>> getClassStatistics() {
        List<ClassInfo> classes = classInfoMapper.selectList(null);
        List<Map<String, Object>> statistics = new ArrayList<>();

        for (ClassInfo classInfo : classes) {
            Map<String, Object> stat = new HashMap<>();
            stat.put("className", classInfo.getClassName());
            stat.put("classCode", classInfo.getClassCode());
            stat.put("studentCount", studentMapper.selectCountByClassId(classInfo.getId()));
            statistics.add(stat);
        }

        return statistics;
    }
}
