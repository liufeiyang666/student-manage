package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ScoreMapper extends BaseMapper<Score> {

    IPage<Score> selectScorePage(Page<Score> page, 
                                  @Param("studentId") Long studentId, 
                                  @Param("courseId") Long courseId,
                                  @Param("semester") String semester);

    List<Score> selectByStudentId(@Param("studentId") Long studentId);

    List<Score> selectByCourseId(@Param("courseId") Long courseId);

    Map<String, Object> selectStatistics(@Param("courseId") Long courseId, @Param("semester") String semester);

    List<Map<String, Object>> selectScoreDistribution(@Param("courseId") Long courseId, @Param("semester") String semester);
}
