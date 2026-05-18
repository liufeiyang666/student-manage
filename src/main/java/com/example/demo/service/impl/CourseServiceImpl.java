package com.example.demo.service.impl;

import com.example.demo.dto.request.CourseCreateRequest;
import com.example.demo.dto.request.CourseUpdateRequest;
import com.example.demo.entity.Course;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList() {
        return courseMapper.selectList(null);
    }

    @Override
    public Course getCourseById(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException(404, "课程不存在");
        }
        return course;
    }

    @Override
    public Course createCourse(CourseCreateRequest request) {
        Course existing = courseMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Course>()
                        .eq(Course::getCourseCode, request.getCourseCode())
        );
        if (existing != null) {
            throw new BusinessException("课程编号已存在");
        }

        Course course = new Course();
        course.setCourseName(request.getCourseName());
        course.setCourseCode(request.getCourseCode());
        course.setCredit(request.getCredit());
        course.setTeacherId(request.getTeacherId());
        course.setDescription(request.getDescription());
        courseMapper.insert(course);

        return course;
    }

    @Override
    public Course updateCourse(Long id, CourseUpdateRequest request) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException(404, "课程不存在");
        }

        if (request.getCourseName() != null) {
            course.setCourseName(request.getCourseName());
        }
        if (request.getCourseCode() != null) {
            course.setCourseCode(request.getCourseCode());
        }
        if (request.getCredit() != null) {
            course.setCredit(request.getCredit());
        }
        if (request.getTeacherId() != null) {
            course.setTeacherId(request.getTeacherId());
        }
        if (request.getDescription() != null) {
            course.setDescription(request.getDescription());
        }
        courseMapper.updateById(course);

        return course;
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException(404, "课程不存在");
        }
        courseMapper.deleteById(id);
    }

    @Override
    public List<Course> getCoursesByTeacherId(Long teacherId) {
        return courseMapper.selectByTeacherId(teacherId);
    }
}
