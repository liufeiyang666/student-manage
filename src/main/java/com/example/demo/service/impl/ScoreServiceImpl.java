package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.request.ScoreCreateRequest;
import com.example.demo.dto.request.ScoreUpdateRequest;
import com.example.demo.entity.Score;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.ScoreMapper;
import com.example.demo.service.ScoreService;
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

    @Override
    public IPage<Score> getScorePage(Integer pageNum, Integer pageSize, Long studentId, Long courseId, String semester) {
        Page<Score> page = new Page<>(pageNum, pageSize);
        IPage<Score> scorePage = scoreMapper.selectScorePage(page, studentId, courseId, semester);
        return scorePage;
    }

    @Override
    public Score getScoreById(Long id) {
        Score score = scoreMapper.selectById(id);
        if (score == null) {
            throw new BusinessException(404, "成绩不存在");
        }
        return score;
    }

    @Override
    public Score createScore(ScoreCreateRequest request) {
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
        score.setTotalScore(calculateTotalScore(request.getUsualScore(), request.getExamScore()));
        score.setSemester(request.getSemester());
        scoreMapper.insert(score);

        return score;
    }

    @Override
    public Score updateScore(Long id, ScoreUpdateRequest request) {
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
        score.setTotalScore(calculateTotalScore(score.getUsualScore(), score.getExamScore()));
        scoreMapper.updateById(score);

        return score;
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
    public List<Score> getScoresByStudentId(Long studentId) {
        return scoreMapper.selectByStudentId(studentId);
    }

    @Override
    public List<Score> getScoresByCourseId(Long courseId) {
        return scoreMapper.selectByCourseId(courseId);
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

        BigDecimal avgScore = (BigDecimal) statistics.get("avgScore");
        BigDecimal maxScore = (BigDecimal) statistics.get("maxScore");
        BigDecimal minScore = (BigDecimal) statistics.get("minScore");
        Integer passCount = (Integer) statistics.get("passCount");
        Integer totalCount = (Integer) statistics.get("totalCount");

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

    private BigDecimal calculateTotalScore(BigDecimal usualScore, BigDecimal examScore) {
        if (usualScore == null || examScore == null) {
            return null;
        }
        return usualScore.multiply(new BigDecimal("0.4"))
                .add(examScore.multiply(new BigDecimal("0.6")))
                .setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
