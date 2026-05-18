package com.example.demo.controller;

import com.example.demo.dto.request.ClassCreateRequest;
import com.example.demo.dto.request.ClassUpdateRequest;
import com.example.demo.entity.ClassInfo;
import com.example.demo.service.ClassService;
import com.example.demo.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
@Tag(name = "班级管理", description = "班级CRUD")
public class ClassController {

    private final ClassService classService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "查询班级列表", description = "获取所有班级列表")
    public Result<List<ClassInfo>> getClassList() {
        List<ClassInfo> classes = classService.getClassList();
        return Result.success(classes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "查询班级详情", description = "根据ID查询班级详情")
    public Result<ClassInfo> getClassById(@PathVariable Long id) {
        ClassInfo classInfo = classService.getClassById(id);
        return Result.success(classInfo);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "新增班级", description = "创建新班级")
    public Result<ClassInfo> createClass(@Valid @RequestBody ClassCreateRequest request) {
        ClassInfo classInfo = classService.createClass(request);
        return Result.success("创建成功", classInfo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "更新班级", description = "更新班级信息")
    public Result<ClassInfo> updateClass(@PathVariable Long id, @RequestBody ClassUpdateRequest request) {
        ClassInfo classInfo = classService.updateClass(id, request);
        return Result.success("更新成功", classInfo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "删除班级", description = "删除班级")
    public Result<?> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return Result.success("删除成功");
    }

    @GetMapping("/{id}/students/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "查询班级学生数量", description = "获取班级学生数量")
    public Result<Integer> getStudentCount(@PathVariable Long id) {
        Integer count = classService.getStudentCount(id);
        return Result.success(count);
    }
}
