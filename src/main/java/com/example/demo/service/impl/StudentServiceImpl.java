package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.request.StudentCreateRequest;
import com.example.demo.dto.request.StudentUpdateRequest;
import com.example.demo.entity.Student;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;

    @Override
    public IPage<Student> getStudentPage(Integer pageNum, Integer pageSize, String name, String studentNo, Long classId) {
        Page<Student> page = new Page<>(pageNum, pageSize);
        IPage<Student> studentPage = studentMapper.selectStudentPage(page, name, studentNo, classId);
        return studentPage;
    }

    @Override
    public Student getStudentById(Long id) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new BusinessException(404, "学生不存在");
        }
        return student;
    }

    @Override
    public Student createStudent(StudentCreateRequest request) {
        Student existing = studentMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Student>()
                        .eq(Student::getStudentNo, request.getStudentNo())
        );
        if (existing != null) {
            throw new BusinessException("学号已存在");
        }

        Student student = new Student();
        student.setStudentNo(request.getStudentNo());
        student.setName(request.getName());
        student.setGender(Integer.valueOf(request.getGender()));
        student.setAge(request.getAge());
        student.setPhone(request.getPhone());
        student.setEmail(request.getEmail());
        student.setClassId(request.getClassId());
        student.setEnrollmentDate(request.getEnrollmentDate());
        student.setAddress(request.getAddress());
        student.setStatus(request.getStatus() != null ? request.getStatus() : 1);
        studentMapper.insert(student);

        return student;
    }

    @Override
    public Student updateStudent(Long id, StudentUpdateRequest request) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new BusinessException(404, "学生不存在");
        }

        if (request.getName() != null) {
            student.setName(request.getName());
        }
        if (request.getGender() != null) {
            student.setGender(Integer.valueOf(request.getGender()));
        }
        if (request.getAge() != null) {
            student.setAge(request.getAge());
        }
        if (request.getPhone() != null) {
            student.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            student.setEmail(request.getEmail());
        }
        if (request.getClassId() != null) {
            student.setClassId(request.getClassId());
        }
        if (request.getEnrollmentDate() != null) {
            student.setEnrollmentDate(request.getEnrollmentDate());
        }
        if (request.getAddress() != null) {
            student.setAddress(request.getAddress());
        }
        if (request.getStatus() != null) {
            student.setStatus(request.getStatus());
        }
        studentMapper.updateById(student);

        return student;
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new BusinessException(404, "学生不存在");
        }
        studentMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void batchDelete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("请选择要删除的学生");
        }
        studentMapper.deleteBatchIds(ids);
    }

    @Override
    public List<Student> getRecentStudents(Integer limit) {
        return studentMapper.selectRecentStudents(limit);
    }
}
