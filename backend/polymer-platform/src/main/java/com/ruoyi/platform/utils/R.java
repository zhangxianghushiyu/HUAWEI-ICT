package com.ruoyi.platform.utils;

import com.ruoyi.platform.constant.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 */
@ApiModel("返回对象")
public class R<T> {
    @ApiModelProperty("接口请求返回状态码")
    private int code;
    @ApiModelProperty("接口请求提示消息")
    private String msg;
    @ApiModelProperty("接口返回数据")
    private T data;
    @ApiModelProperty("是否请求成功")
    private boolean success;

    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = true;
    }

    public R() {
        this.code = Constant.ResultCode.SUCCESS.getCode();
        this.msg = Constant.ResultCode.SUCCESS.getMsg();
        //应前端要求，data返回null
//        this.data = (T)"";
        this.success = true;
    }

    public R(T data) {
        this.code = Constant.ResultCode.SUCCESS.getCode();
        this.msg = Constant.ResultCode.SUCCESS.getMsg();
        this.data = data;
        this.success = true;
    }

    public R(int code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
//        this.data = (T)"";
        this.success = success;
    }

    public R(int code, String msg, T data, boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    public static R ok() {
        return new R();
    }

    public static <T> R<T> ok(T data) {
        return new R<T>(data);
    }

    public static <T> R<T> ok(int code, T data , String msg) {
        return new R(code, msg, data, true);
    }

    public static R error(int code, Object data, String msg) {
        return new R(code, msg, data, false);
    }

    public static R error(String msg) {
        return new R(Constant.ResultCode.ERROR.getCode(), msg, false);
    }

    public static R error(int code, String msg) {
        return new R(code, msg, false);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
