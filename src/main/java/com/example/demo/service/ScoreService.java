package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.request.ScoreCreateRequest;
import com.example.demo.dto.request.ScoreUpdateRequest;
import com.example.demo.vo.ScoreVO;

import java.util.List;
import java.util.Map;

public interface ScoreService {

    IPage<ScoreVO> getScorePage(Integer pageNum, Integer pageSize, Long studentId, Long courseId, String semester);

    ScoreVO getScoreById(Long id);

    ScoreVO createScore(ScoreCreateRequest request);

    ScoreVO updateScore(Long id, ScoreUpdateRequest request);

    void deleteScore(Long id);

    List<ScoreVO> getScoresByStudentId(Long studentId);

    List<ScoreVO> getScoresByCourseId(Long courseId);

    Map<String, Object> getStatistics(Long courseId, String semester);

    List<Map<String, Object>> getScoreDistribution(Long courseId, String semester);
}
