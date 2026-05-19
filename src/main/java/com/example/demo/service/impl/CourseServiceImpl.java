package com.example.demo.service.impl;

import com.example.demo.dto.request.CourseCreateRequest;
import com.example.demo.dto.request.CourseUpdateRequest;
import com.example.demo.entity.Course;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.service.CourseService;
import com.example.demo.vo.CourseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private final TeacherMapper teacherMapper;

    @Override
    public List<CourseVO> getCourseList() {
        List<Course> courses = courseMapper.selectList(null);
        return courses.stream().map(this::convertToVO).toList();
    }

    @Override
    public CourseVO getCourseById(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException(404, "课程不存在");
        }
        return convertToVO(course);
    }

    @Override
    public CourseVO createCourse(CourseCreateRequest request) {
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

        return convertToVO(course);
    }

    @Override
    public CourseVO updateCourse(Long id, CourseUpdateRequest request) {
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

        return convertToVO(course);
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
    public List<CourseVO> getCoursesByTeacherId(Long teacherId) {
        List<Course> courses = courseMapper.selectByTeacherId(teacherId);
        return courses.stream().map(this::convertToVO).toList();
    }

    private CourseVO convertToVO(Course course) {
        CourseVO vo = new CourseVO();
        vo.setId(course.getId());
        vo.setCourseName(course.getCourseName());
        vo.setCourseCode(course.getCourseCode());
        vo.setCredit(course.getCredit());
        vo.setTeacherId(course.getTeacherId());
        vo.setDescription(course.getDescription());
        vo.setCreateTime(course.getCreateTime());
        vo.setUpdateTime(course.getUpdateTime());

        if (course.getTeacherId() != null) {
            var teacher = teacherMapper.selectById(course.getTeacherId());
            if (teacher != null) {
                vo.setTeacherName(teacher.getName());
            }
        }

        return vo;
    }
}
