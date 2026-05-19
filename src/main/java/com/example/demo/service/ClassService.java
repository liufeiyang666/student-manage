package com.example.demo.service;

import com.example.demo.dto.request.ClassCreateRequest;
import com.example.demo.dto.request.ClassUpdateRequest;
import com.example.demo.vo.ClassVO;

import java.util.List;

public interface ClassService {

    List<ClassVO> getClassList();

    ClassVO getClassById(Long id);

    ClassVO createClass(ClassCreateRequest request);

    ClassVO updateClass(Long id, ClassUpdateRequest request);

    void deleteClass(Long id);

    Integer getStudentCount(Long classId);
}
