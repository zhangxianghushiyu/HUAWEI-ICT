package com.ruoyi.platform.config.exception;

import com.ruoyi.platform.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 全局异常处理器
 *
 * @author ecargo
 */
@ResponseBody
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 通用错误一般用于 catch 抛出异常的时候 详情见消息体
     */
    public static final Integer GENERAL_ERROR = 500;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = ApiException.class)
    public R handle(ApiException e) {
        if (Objects.nonNull(e.getErrorCode())) {
            return R.error(e.getErrorCode().getCode(), e.getMessage());
        }
        return R.error(e.getErrorCode().getCode(), e.getMessage());
    }

    //处理通用异常
    @ExceptionHandler(value = Exception.class)
    public R defaultErrorHandler(HttpServletRequest req, Exception e) {
        log.error(e.toString(), e);
        String errorMessage = getErrorMessage(e);
        StringBuilder sb = new StringBuilder(errorMessage);
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            String s = stackTraceElement.toString();
            if (s.startsWith("com.jn")) {
                sb.append(s).append("\n");
            }
        }
        return R.error(GENERAL_ERROR, sb.toString());
    }

    private static String getErrorMessage(Exception e) {
        String errorMessage = e.getMessage();
        if (errorMessage.contains("default message [") && errorMessage.contains("Validation failed")) {
            try {
                //截取关键信息
                errorMessage = errorMessage.substring(errorMessage.lastIndexOf("default message [") + 15);
                //替换冗余字段
                errorMessage = errorMessage.replace("[", "").replace("]", "");
                //去除空格
                errorMessage = errorMessage.trim();
            }catch (Exception e1) {
                errorMessage = e.getMessage();
            }
        }
        return errorMessage;
    }


    @ExceptionHandler(CustomException.class)//可以将Exception中的异常信息以Json的信息返回给前端
    public R handle(CustomException e) {
        log.error("状态码:" + e.getErrorCode(), e);
        return R.error(e.getMessage());
    }
}
