package com.ruoyi.platform.enums;

import com.ruoyi.platform.constant.IErrorCode;

/**
 *
 */
public enum ResultCode implements IErrorCode {
    NO_USER_INFO(700, "没有获取到用户信息"),
    NO_GET_PARAMS_CONFIG_INFO(500, "没有获取到配置信息"),
    FUNCTION_NOT_OPEN(500, "该功能未开放"),
    USER_STOP_USING(701, "该账号已停用"),
    USER_DELETED(702, "该账号不存在"),
    USERNAME_PASSWORD_FAIL(703, "账号或密码错误"),
    OLD_PASSWORD_FAIL(704, "旧密码不正确"),
    NEW_PASSWORD_FAIL(705, "新密码输入不一致"),
    FAILED(5002, "操作失败");


    private final int code;
    private final String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
