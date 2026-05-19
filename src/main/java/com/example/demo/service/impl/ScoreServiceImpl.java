package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.request.ScoreCreateRequest;
import com.example.demo.dto.request.ScoreUpdateRequest;
import com.example.demo.entity.Score;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.*;
import com.example.demo.service.ScoreService;
import com.example.demo.vo.ScoreVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final ScoreMapper scoreMapper;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final ClassInfoMapper classInfoMapper;
    private final TeacherMapper teacherMapper;

    @Override
    public IPage<ScoreVO> getScorePage(Integer pageNum, Integer pageSize, Long studentId, Long courseId, String semester) {
        Page<Score> page = new Page<>(pageNum, pageSize);
        IPage<Score> scorePage = scoreMapper.selectScorePage(page, studentId, courseId, semester);

        Page<ScoreVO> voPage = new Page<>(pageNum, pageSize);
        voPage.setTotal(scorePage.getTotal());
        voPage.setRecords(scorePage.getRecords().stream().map(this::convertToVO).toList());

        return voPage;
    }

    @Override
    public ScoreVO getScoreById(Long id) {
        Score score = scoreMapper.selectById(id);
        if (score == null) {
            throw new BusinessException(404, "成绩不存在");
        }
        return convertToVO(score);
    }

    @Override
    public ScoreVO createScore(ScoreCreateRequest request) {
        Score existing = scoreMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Score>()
                        .eq(Score::getStudentId, request.getStudentId())
                        .eq(Score::getCourseId, request.getCourseId())
                        .eq(Score::getSemester, request.getSemester())
        );
        if (existing != null) {
            throw new BusinessException("该学生在该学期的该课程成绩已存在");
        }

        Score score = new Score();
        score.setStudentId(request.getStudentId());
        score.setCourseId(request.getCourseId());
        score.setUsualScore(request.getUsualScore());
        score.setExamScore(request.getExamScore());
        score.setSemester(request.getSemester());
        scoreMapper.insert(score);

        return convertToVO(score);
    }

    @Override
    public ScoreVO updateScore(Long id, ScoreUpdateRequest request) {
        Score score = scoreMapper.selectById(id);
        if (score == null) {
            throw new BusinessException(404, "成绩不存在");
        }

        if (request.getUsualScore() != null) {
            score.setUsualScore(request.getUsualScore());
        }
        if (request.getExamScore() != null) {
            score.setExamScore(request.getExamScore());
        }
        if (request.getSemester() != null) {
            score.setSemester(request.getSemester());
        }
        scoreMapper.updateById(score);

        return convertToVO(score);
    }

    @Override
    public void deleteScore(Long id) {
        Score score = scoreMapper.selectById(id);
        if (score == null) {
            throw new BusinessException(404, "成绩不存在");
        }
        scoreMapper.deleteById(id);
    }

    @Override
    public List<ScoreVO> getScoresByStudentId(Long studentId) {
        List<Score> scores = scoreMapper.selectByStudentId(studentId);
        return scores.stream().map(this::convertToVO).toList();
    }

    @Override
    public List<ScoreVO> getScoresByCourseId(Long courseId) {
        List<Score> scores = scoreMapper.selectByCourseId(courseId);
        return scores.stream().map(this::convertToVO).toList();
    }

    @Override
    public Map<String, Object> getStatistics(Long courseId, String semester) {
        Map<String, Object> statistics = scoreMapper.selectStatistics(courseId, semester);
        if (statistics == null) {
            statistics = new HashMap<>();
            statistics.put("avgScore", BigDecimal.ZERO);
            statistics.put("maxScore", BigDecimal.ZERO);
            statistics.put("minScore", BigDecimal.ZERO);
            statistics.put("passCount", 0);
            statistics.put("totalCount", 0);
        }

        BigDecimal avgScore = statistics.get("avgScore") != null ? toBigDecimal(statistics.get("avgScore")) : BigDecimal.ZERO;
        BigDecimal maxScore = statistics.get("maxScore") != null ? toBigDecimal(statistics.get("maxScore")) : BigDecimal.ZERO;
        BigDecimal minScore = statistics.get("minScore") != null ? toBigDecimal(statistics.get("minScore")) : BigDecimal.ZERO;
        Long passCountLong = statistics.get("passCount") != null ? ((Number) statistics.get("passCount")).longValue() : 0L;
        Long totalCountLong = statistics.get("totalCount") != null ? ((Number) statistics.get("totalCount")).longValue() : 0L;
        Integer passCount = passCountLong.intValue();
        Integer totalCount = totalCountLong.intValue();

        double passRate = totalCount != null && totalCount > 0 ? (passCount * 100.0 / totalCount) : 0;

        Map<String, Object> result = new HashMap<>();
        result.put("avgScore", avgScore != null ? avgScore : BigDecimal.ZERO);
        result.put("maxScore", maxScore != null ? maxScore : BigDecimal.ZERO);
        result.put("minScore", minScore != null ? minScore : BigDecimal.ZERO);
        result.put("passCount", passCount != null ? passCount : 0);
        result.put("totalCount", totalCount != null ? totalCount : 0);
        result.put("passRate", Math.round(passRate * 100.0) / 100.0);

        return result;
    }

    @Override
    public List<Map<String, Object>> getScoreDistribution(Long courseId, String semester) {
        return scoreMapper.selectScoreDistribution(courseId, semester);
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        } else if (value instanceof String) {
            return new BigDecimal((String) value);
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal calculateTotalScore(BigDecimal usualScore, BigDecimal examScore) {
        if (usualScore == null || examScore == null) {
            return null;
        }
        return usualScore.multiply(new BigDecimal("0.4"))
                .add(examScore.multiply(new BigDecimal("0.6")))
                .setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private ScoreVO convertToVO(Score score) {
        ScoreVO vo = new ScoreVO();
        vo.setId(score.getId());
        vo.setStudentId(score.getStudentId());
        vo.setCourseId(score.getCourseId());
        vo.setUsualScore(score.getUsualScore());
        vo.setExamScore(score.getExamScore());
        vo.setTotalScore(score.getTotalScore());
        vo.setSemester(score.getSemester());
        vo.setCreateTime(score.getCreateTime());
        vo.setUpdateTime(score.getUpdateTime());

        var student = studentMapper.selectById(score.getStudentId());
        if (student != null) {
            vo.setStudentNo(student.getStudentNo());
            vo.setStudentName(student.getName());
            vo.setClassName(classInfoMapper.selectById(student.getClassId()) != null ?
                    classInfoMapper.selectById(student.getClassId()).getClassName() : "");
        }

        var course = courseMapper.selectById(score.getCourseId());
        if (course != null) {
            vo.setCourseName(course.getCourseName());
            vo.setCourseCode(course.getCourseCode());
            vo.setTeacherName(teacherMapper.selectById(course.getTeacherId()) != null ?
                    teacherMapper.selectById(course.getTeacherId()).getName() : "");
        }

        return vo;
    }
}
