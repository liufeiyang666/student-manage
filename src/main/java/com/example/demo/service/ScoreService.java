package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.request.ScoreCreateRequest;
import com.example.demo.dto.request.ScoreUpdateRequest;
import com.example.demo.entity.Score;

import java.util.List;
import java.util.Map;

public interface ScoreService {

    IPage<Score> getScorePage(Integer pageNum, Integer pageSize, Long studentId, Long courseId, String semester);

    Score getScoreById(Long id);

    Score createScore(ScoreCreateRequest request);

    Score updateScore(Long id, ScoreUpdateRequest request);

    void deleteScore(Long id);

    List<Score> getScoresByStudentId(Long studentId);

    List<Score> getScoresByCourseId(Long courseId);

    Map<String, Object> getStatistics(Long courseId, String semester);

    List<Map<String, Object>> getScoreDistribution(Long courseId, String semester);
}
