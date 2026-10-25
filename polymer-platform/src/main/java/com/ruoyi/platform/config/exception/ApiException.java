package com.ruoyi.platform.config.exception;


import com.ruoyi.platform.constant.IErrorCode;
import com.ruoyi.platform.enums.InfoResultCode;

/**
 * 自定义API异常
 */
public class ApiException extends RuntimeException {


    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
        this.errorCode = new InfoResultCode(500, message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Integer code, String msg) {
        super(msg);
        this.errorCode = new InfoResultCode(code, msg);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
