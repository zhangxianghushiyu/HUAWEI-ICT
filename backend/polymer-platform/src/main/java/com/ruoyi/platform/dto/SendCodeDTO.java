package com.ruoyi.platform.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="发送验证码入参对象")
public class SendCodeDTO implements Serializable {

    @NotBlank(message = "场景（1：注册+登录）不能为空！")
    @ApiModelProperty(value="场景（1：注册+登录）")
    @Min(value = 1, message = "类型不合法")
    @Max(value = 1, message = "类型不合法")
    private String scene;

    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "请输入正确的手机号")
    @NotBlank(message = "手机号不能为空！")
    @ApiModelProperty(value="手机号")
    private String phone;

}
