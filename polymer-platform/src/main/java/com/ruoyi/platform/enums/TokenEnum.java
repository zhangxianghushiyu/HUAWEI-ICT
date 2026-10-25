package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum TokenEnum {


    TOKEN_IS_NULL(601, "获取token失败"),

    TOKEN_IS_EXPIRE(602, "token已失效或者不存在"),

    TOKEN_REPEAT_LOGIN(603, "已在其他位置登陆");

    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (TokenEnum e : TokenEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
