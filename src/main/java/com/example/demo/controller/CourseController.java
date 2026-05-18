package com.example.demo.controller;

import com.example.demo.dto.request.CourseCreateRequest;
import com.example.demo.dto.request.CourseUpdateRequest;
import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import com.example.demo.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "课程管理", description = "课程CRUD")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    @Operation(summary = "查询课程列表", description = "获取所有课程列表")
    public Result<List<Course>> getCourseList() {
        List<Course> courses = courseService.getCourseList();
        return Result.success(courses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    @Operation(summary = "查询课程详情", description = "根据ID查询课程详情")
    public Result<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return Result.success(course);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "新增课程", description = "创建新课程")
    public Result<Course> createCourse(@Valid @RequestBody CourseCreateRequest request) {
        Course course = courseService.createCourse(request);
        return Result.success("创建成功", course);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "更新课程", description = "更新课程信息")
    public Result<Course> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateRequest request) {
        Course course = courseService.updateCourse(id, request);
        return Result.success("更新成功", course);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "删除课程", description = "删除课程")
    public Result<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return Result.success("删除成功");
    }

    @GetMapping("/teacher/{teacherId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "根据教师查询课程", description = "获取教师所授课程")
    public Result<List<Course>> getCoursesByTeacherId(@PathVariable Long teacherId) {
        List<Course> courses = courseService.getCoursesByTeacherId(teacherId);
        return Result.success(courses);
    }
}
