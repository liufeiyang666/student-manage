package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.request.StudentCreateRequest;
import com.example.demo.dto.request.StudentUpdateRequest;
import com.example.demo.vo.StudentVO;

import java.util.List;

public interface StudentService {

    IPage<StudentVO> getStudentPage(Integer pageNum, Integer pageSize, String name, String studentNo, Long classId);

    StudentVO getStudentById(Long id);

    StudentVO createStudent(StudentCreateRequest request);

    StudentVO updateStudent(Long id, StudentUpdateRequest request);

    void deleteStudent(Long id);

    void batchDelete(List<Long> ids);

    List<StudentVO> getRecentStudents(Integer limit);
}
