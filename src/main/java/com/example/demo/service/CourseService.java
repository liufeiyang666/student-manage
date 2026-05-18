package com.example.demo.service;

import com.example.demo.dto.request.CourseCreateRequest;
import com.example.demo.dto.request.CourseUpdateRequest;
import com.example.demo.entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> getCourseList();

    Course getCourseById(Long id);

    Course createCourse(CourseCreateRequest request);

    Course updateCourse(Long id, CourseUpdateRequest request);

    void deleteCourse(Long id);

    List<Course> getCoursesByTeacherId(Long teacherId);
}
