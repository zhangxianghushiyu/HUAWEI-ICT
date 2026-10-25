package com.ruoyi.platform.enums;

import com.ruoyi.platform.constant.IErrorCode;
import lombok.Data;

/**
 * 消息功能API操作码
 */
@Data
public class InfoResultCode implements IErrorCode {

    private Integer code;
    private String message;

    public InfoResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public InfoResultCode(String message) {
        this.code = 500;
        this.message = message;
    }
}
