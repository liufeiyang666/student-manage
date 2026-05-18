package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.request.ScoreCreateRequest;
import com.example.demo.dto.request.ScoreUpdateRequest;
import com.example.demo.entity.Score;
import com.example.demo.service.ScoreService;
import com.example.demo.utils.PageResult;
import com.example.demo.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scores")
@RequiredArgsConstructor
@Tag(name = "成绩管理", description = "成绩CRUD、统计")
public class ScoreController {

    private final ScoreService scoreService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "查询成绩列表", description = "分页查询成绩列表")
    public Result<PageResult<Score>> getScorePage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String semester) {
        IPage<Score> page = scoreService.getScorePage(pageNum, pageSize, studentId, courseId, semester);
        PageResult<Score> result = PageResult.of(page.getRecords(), page.getTotal(), pageNum, pageSize);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "查询成绩详情", description = "根据ID查询成绩详情")
    public Result<Score> getScoreById(@PathVariable Long id) {
        Score score = scoreService.getScoreById(id);
        return Result.success(score);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "录入成绩", description = "创建新成绩")
    public Result<Score> createScore(@Valid @RequestBody ScoreCreateRequest request) {
        Score score = scoreService.createScore(request);
        return Result.success("录入成功", score);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "更新成绩", description = "更新成绩信息")
    public Result<Score> updateScore(@PathVariable Long id, @RequestBody ScoreUpdateRequest request) {
        Score score = scoreService.updateScore(id, request);
        return Result.success("更新成功", score);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "删除成绩", description = "删除成绩")
    public Result<?> deleteScore(@PathVariable Long id) {
        scoreService.deleteScore(id);
        return Result.success("删除成功");
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    @Operation(summary = "按学生查询成绩", description = "获取学生所有成绩")
    public Result<List<Score>> getScoresByStudentId(@PathVariable Long studentId) {
        List<Score> scores = scoreService.getScoresByStudentId(studentId);
        return Result.success(scores);
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "按课程查询成绩", description = "获取课程所有成绩")
    public Result<List<Score>> getScoresByCourseId(@PathVariable Long courseId) {
        List<Score> scores = scoreService.getScoresByCourseId(courseId);
        return Result.success(scores);
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "成绩统计", description = "获取成绩统计信息")
    public Result<Map<String, Object>> getStatistics(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String semester) {
        Map<String, Object> statistics = scoreService.getStatistics(courseId, semester);
        return Result.success(statistics);
    }
}
