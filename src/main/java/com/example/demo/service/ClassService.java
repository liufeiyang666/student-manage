package com.example.demo.service;

import com.example.demo.dto.request.ClassCreateRequest;
import com.example.demo.dto.request.ClassUpdateRequest;
import com.example.demo.entity.ClassInfo;

import java.util.List;

public interface ClassService {

    List<ClassInfo> getClassList();

    ClassInfo getClassById(Long id);

    ClassInfo createClass(ClassCreateRequest request);

    ClassInfo updateClass(Long id, ClassUpdateRequest request);

    void deleteClass(Long id);

    Integer getStudentCount(Long classId);
}
