package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.request.StudentCreateRequest;
import com.example.demo.dto.request.StudentUpdateRequest;
import com.example.demo.service.StudentService;
import com.example.demo.utils.PageResult;
import com.example.demo.utils.Result;
import com.example.demo.vo.StudentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "学生管理", description = "学生CRUD、批量删除")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "查询学生列表", description = "分页查询学生列表，支持搜索")
    public Result<PageResult<StudentVO>> getStudentPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String studentNo,
            @RequestParam(required = false) Long classId) {
        IPage<StudentVO> page = studentService.getStudentPage(pageNum, pageSize, name, studentNo, classId);
        PageResult<StudentVO> result = PageResult.of(page.getRecords(), page.getTotal(), pageNum, pageSize);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "查询学生详情", description = "根据ID查询学生详情")
    public Result<StudentVO> getStudentById(@PathVariable Long id) {
        StudentVO studentVO = studentService.getStudentById(id);
        return Result.success(studentVO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "新增学生", description = "创建新学生")
    public Result<StudentVO> createStudent(@Valid @RequestBody StudentCreateRequest request) {
        StudentVO studentVO = studentService.createStudent(request);
        return Result.success("创建成功", studentVO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "更新学生", description = "更新学生信息")
    public Result<StudentVO> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateRequest request) {
        StudentVO studentVO = studentService.updateStudent(id, request);
        return Result.success("更新成功", studentVO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "删除学生", description = "删除学生")
    public Result<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return Result.success("删除成功");
    }

    @DeleteMapping("/batch")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "批量删除学生", description = "批量删除学生")
    public Result<?> batchDelete(@RequestBody List<Long> ids) {
        studentService.batchDelete(ids);
        return Result.success("批量删除成功");
    }
}
