package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否自动到账
 */
@Getter
@AllArgsConstructor
public enum AutoAccountEnum {


    TYPE_0(0, "否"),

    TYPE_1(1, "是");


    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (AutoAccountEnum e : AutoAccountEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
