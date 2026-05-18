package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper extends BaseMapper<Student> {

    IPage<Student> selectStudentPage(Page<Student> page, 
                                     @Param("name") String name, 
                                     @Param("studentNo") String studentNo, 
                                     @Param("classId") Long classId);

    List<Student> selectRecentStudents(@Param("limit") Integer limit);

    Integer selectCountByClassId(@Param("classId") Long classId);
}
