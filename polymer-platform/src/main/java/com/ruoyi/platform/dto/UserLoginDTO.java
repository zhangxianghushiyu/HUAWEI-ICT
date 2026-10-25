package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(value="手机号发送验证码登录入参对象")
public class UserLoginDTO implements Serializable {

    @NotBlank(message = "场景（1：注册+登录）不能为空！")
    @ApiModelProperty(value="场景（1：注册+登录）")
    @Min(value = 1, message = "类型不合法")
    @Max(value = 1, message = "类型不合法")
    private String scene;

    @NotBlank(message = "验证码不能为空！")
    @ApiModelProperty(value="验证码")
    private String code;

    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "请输入正确的手机号")
    @NotBlank(message = "手机号不能为空！")
    @ApiModelProperty(value="手机号")
    private String phone;

}
