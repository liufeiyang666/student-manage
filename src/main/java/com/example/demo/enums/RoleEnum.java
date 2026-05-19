package com.example.demo.enums;

public enum RoleEnum {

    ADMIN("ADMIN", "管理员"),
    TEACHER("TEACHER", "教师"),
    STUDENT("STUDENT", "学生");

    private final String code;
    private final String name;

    RoleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RoleEnum getByCode(String code) {
        for (RoleEnum role : values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        return null;
    }
}
