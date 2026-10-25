package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value="账号登录入参对象")
public class AccountLoginDTO implements Serializable {

    @NotBlank(message = "账号不能为空！")
    @ApiModelProperty(value="账号")
    private String userName;

    @NotBlank(message = "密码不能为空！")
    @ApiModelProperty(value="密码")
    private String password;

}
