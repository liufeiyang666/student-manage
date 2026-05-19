package com.example.demo.enums;

public enum StatusEnum {

    DISABLED(0, "禁用"),
    ENABLED(1, "启用"),
    GRADUATED(2, "毕业"),
    SUSPENDED(0, "休学"),
    STUDYING(1, "在读");

    private final Integer code;
    private final String name;

    StatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static StatusEnum getByCode(Integer code) {
        for (StatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
