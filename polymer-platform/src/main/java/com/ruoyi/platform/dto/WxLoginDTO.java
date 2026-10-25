package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value="微信登录入参对象")
public class WxLoginDTO implements Serializable {

    @NotBlank(message = "微信code不能为空！")
    @ApiModelProperty(value="微信code")
    private String code;

}
