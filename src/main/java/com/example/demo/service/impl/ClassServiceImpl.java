package com.example.demo.service.impl;

import com.example.demo.dto.request.ClassCreateRequest;
import com.example.demo.dto.request.ClassUpdateRequest;
import com.example.demo.entity.ClassInfo;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.ClassInfoMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.service.ClassService;
import com.example.demo.vo.ClassVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassInfoMapper classInfoMapper;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;

    @Override
    public List<ClassVO> getClassList() {
        List<ClassInfo> classes = classInfoMapper.selectList(null);
        return classes.stream().map(this::convertToVO).toList();
    }

    @Override
    public ClassVO getClassById(Long id) {
        ClassInfo classInfo = classInfoMapper.selectById(id);
        if (classInfo == null) {
            throw new BusinessException(404, "班级不存在");
        }
        return convertToVO(classInfo);
    }

    @Override
    public ClassVO createClass(ClassCreateRequest request) {
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

        return convertToVO(classInfo);
    }

    @Override
    public ClassVO updateClass(Long id, ClassUpdateRequest request) {
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

        return convertToVO(classInfo);
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

    private ClassVO convertToVO(ClassInfo classInfo) {
        ClassVO vo = new ClassVO();
        vo.setId(classInfo.getId());
        vo.setClassName(classInfo.getClassName());
        vo.setClassCode(classInfo.getClassCode());
        vo.setTeacherId(classInfo.getTeacherId());
        vo.setDescription(classInfo.getDescription());
        vo.setCreateTime(classInfo.getCreateTime());
        vo.setUpdateTime(classInfo.getUpdateTime());
        vo.setStudentCount(studentMapper.selectCountByClassId(classInfo.getId()));

        if (classInfo.getTeacherId() != null) {
            var teacher = teacherMapper.selectById(classInfo.getTeacherId());
            if (teacher != null) {
                vo.setTeacherName(teacher.getName());
            }
        }

        return vo;
    }
}
