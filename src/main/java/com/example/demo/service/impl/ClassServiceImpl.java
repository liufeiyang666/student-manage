package com.example.demo.service.impl;

import com.example.demo.dto.request.ClassCreateRequest;
import com.example.demo.dto.request.ClassUpdateRequest;
import com.example.demo.entity.ClassInfo;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.ClassInfoMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassInfoMapper classInfoMapper;
    private final StudentMapper studentMapper;

    @Override
    public List<ClassInfo> getClassList() {
        return classInfoMapper.selectList(null);
    }

    @Override
    public ClassInfo getClassById(Long id) {
        ClassInfo classInfo = classInfoMapper.selectById(id);
        if (classInfo == null) {
            throw new BusinessException(404, "班级不存在");
        }
        return classInfo;
    }

    @Override
    public ClassInfo createClass(ClassCreateRequest request) {
        ClassInfo existing = classInfoMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<ClassInfo>()
                        .eq(ClassInfo::getClassCode, request.getClassCode())
        );
        if (existing != null) {
            throw new BusinessException("班级编号已存在");
        }

        ClassInfo classInfo = new ClassInfo();
        classInfo.setClassName(request.getClassName());
        classInfo.setClassCode(request.getClassCode());
        classInfo.setTeacherId(request.getTeacherId());
        classInfo.setDescription(request.getDescription());
        classInfoMapper.insert(classInfo);

        return classInfo;
    }

    @Override
    public ClassInfo updateClass(Long id, ClassUpdateRequest request) {
        ClassInfo classInfo = classInfoMapper.selectById(id);
        if (classInfo == null) {
            throw new BusinessException(404, "班级不存在");
        }

        if (request.getClassName() != null) {
            classInfo.setClassName(request.getClassName());
        }
        if (request.getClassCode() != null) {
            classInfo.setClassCode(request.getClassCode());
        }
        if (request.getTeacherId() != null) {
            classInfo.setTeacherId(request.getTeacherId());
        }
        if (request.getDescription() != null) {
            classInfo.setDescription(request.getDescription());
        }
        classInfoMapper.updateById(classInfo);

        return classInfo;
    }

    @Override
    public void deleteClass(Long id) {
        ClassInfo classInfo = classInfoMapper.selectById(id);
        if (classInfo == null) {
            throw new BusinessException(404, "班级不存在");
        }
        
        Integer count = studentMapper.selectCountByClassId(id);
        if (count != null && count > 0) {
            throw new BusinessException("该班级下还有学生，无法删除");
        }
        
        classInfoMapper.deleteById(id);
    }

    @Override
    public Integer getStudentCount(Long classId) {
        return studentMapper.selectCountByClassId(classId);
    }
}
