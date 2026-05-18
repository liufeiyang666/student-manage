package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("student")
public class Student {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("student_no")
    private String studentNo;

    @TableField("user_id")
    private Long userId;

    @TableField("name")
    private String name;

    @TableField("gender")
    private Integer gender;

    @TableField("age")
    private Integer age;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @TableField("class_id")
    private Long classId;

    @TableField("enrollment_date")
    private LocalDate enrollmentDate;

    @TableField("address")
    private String address;

    @TableField("status")
    private Integer status;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
