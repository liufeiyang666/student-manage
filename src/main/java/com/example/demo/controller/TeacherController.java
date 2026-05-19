package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.request.TeacherCreateRequest;
import com.example.demo.dto.request.TeacherUpdateRequest;
import com.example.demo.service.TeacherService;
import com.example.demo.utils.PageResult;
import com.example.demo.utils.Result;
import com.example.demo.vo.TeacherVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Tag(name = "教师管理", description = "教师CRUD接口")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "获取教师列表", description = "获取所有教师列表")
    public Result<PageResult<TeacherVO>> getTeacherPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<TeacherVO> page = teacherService.getTeacherPage(pageNum, pageSize);
        PageResult<TeacherVO> result = PageResult.of(page.getRecords(), page.getTotal(), pageNum, pageSize);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "查询教师详情", description = "根据ID查询教师详情")
    public Result<TeacherVO> getTeacherById(@PathVariable Long id) {
        TeacherVO teacher = teacherService.getTeacherById(id);
        return Result.success(teacher);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "新增教师", description = "创建新教师")
    public Result<TeacherVO> createTeacher(@Valid @RequestBody TeacherCreateRequest request) {
        TeacherVO teacher = teacherService.createTeacher(request);
        return Result.success("创建成功", teacher);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "更新教师", description = "更新教师信息")
    public Result<TeacherVO> updateTeacher(@PathVariable Long id, @RequestBody TeacherUpdateRequest request) {
        TeacherVO teacher = teacherService.updateTeacher(id, request);
        return Result.success("更新成功", teacher);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "删除教师", description = "删除教师")
    public Result<?> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return Result.success("删除成功");
    }
}
