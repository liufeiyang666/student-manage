package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.request.StudentCreateRequest;
import com.example.demo.dto.request.StudentUpdateRequest;
import com.example.demo.entity.Student;

import java.util.List;

public interface StudentService {

    IPage<Student> getStudentPage(Integer pageNum, Integer pageSize, String name, String studentNo, Long classId);

    Student getStudentById(Long id);

    Student createStudent(StudentCreateRequest request);

    Student updateStudent(Long id, StudentUpdateRequest request);

    void deleteStudent(Long id);

    void batchDelete(List<Long> ids);

    List<Student> getRecentStudents(Integer limit);
}
