package com.example.demo.service;

import com.example.demo.dto.request.CourseCreateRequest;
import com.example.demo.dto.request.CourseUpdateRequest;
import com.example.demo.vo.CourseVO;

import java.util.List;

public interface CourseService {

    List<CourseVO> getCourseList();

    CourseVO getCourseById(Long id);

    CourseVO createCourse(CourseCreateRequest request);

    CourseVO updateCourse(Long id, CourseUpdateRequest request);

    void deleteCourse(Long id);

    List<CourseVO> getCoursesByTeacherId(Long teacherId);
}
