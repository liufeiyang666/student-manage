package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.request.TeacherCreateRequest;
import com.example.demo.dto.request.TeacherUpdateRequest;
import com.example.demo.vo.TeacherVO;

public interface TeacherService {
    IPage<TeacherVO> getTeacherPage(Integer pageNum, Integer pageSize);

    TeacherVO getTeacherById(Long id);

    TeacherVO createTeacher(TeacherCreateRequest request);

    TeacherVO updateTeacher(Long id, TeacherUpdateRequest request);

    void deleteTeacher(Long id);
}
