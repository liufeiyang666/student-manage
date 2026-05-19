package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.request.TeacherCreateRequest;
import com.example.demo.dto.request.TeacherUpdateRequest;
import com.example.demo.entity.Teacher;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.service.TeacherService;
import com.example.demo.vo.TeacherVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;

    @Override
    public IPage<TeacherVO> getTeacherPage(Integer pageNum, Integer pageSize) {
        Page<Teacher> page = new Page<>(pageNum, pageSize);
        IPage<Teacher> teacherPage = teacherMapper.selectMyPage(page);
        Page<TeacherVO> voPage = new Page<>(pageNum, pageSize);
        voPage.setTotal(teacherPage.getTotal());
        voPage.setRecords(teacherPage.getRecords().stream().map(this::convertToVO).toList());
        return voPage;
    }

    @Override
    public TeacherVO getTeacherById(Long id) {
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            throw new BusinessException(404, "教师不存在");
        }
        return convertToVO(teacher);
    }

    @Override
    public TeacherVO createTeacher(TeacherCreateRequest request) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getTeacherNo, request.getTeacherNo());
        if (teacherMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("教师编号已存在");
        }

        Teacher teacher = new Teacher();
        teacher.setTeacherNo(request.getTeacherNo());
        teacher.setName(request.getName());
        teacher.setGender(request.getGender());
        teacher.setAge(request.getAge());
        teacher.setPhone(request.getPhone());
        teacher.setEmail(request.getEmail());
        teacherMapper.insert(teacher);

        return convertToVO(teacher);
    }

    @Override
    public TeacherVO updateTeacher(Long id, TeacherUpdateRequest request) {
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            throw new BusinessException(404, "教师不存在");
        }

        if (request.getName() != null) {
            teacher.setName(request.getName());
        }
        if (request.getGender() != null) {
            teacher.setGender(request.getGender());
        }
        if (request.getAge() != null) {
            teacher.setAge(request.getAge());
        }
        if (request.getPhone() != null) {
            teacher.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            teacher.setEmail(request.getEmail());
        }
        teacherMapper.updateById(teacher);

        return convertToVO(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            throw new BusinessException(404, "教师不存在");
        }
        teacherMapper.deleteById(id);
    }

    private TeacherVO convertToVO(Teacher teacher) {
        TeacherVO vo = new TeacherVO();
        vo.setId(teacher.getId());
        vo.setTeacherNo(teacher.getTeacherNo());
        vo.setName(teacher.getName());
        vo.setGender(teacher.getGender());
        vo.setAge(teacher.getAge());
        vo.setPhone(teacher.getPhone());
        vo.setEmail(teacher.getEmail());
        return vo;
    }
}
