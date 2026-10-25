package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value="修改密码入参对象")
public class ModifyPasswordDTO implements Serializable {

    @NotBlank(message = "原密码不能为空！")
    @ApiModelProperty(value="原密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空！")
    @ApiModelProperty(value="新密码")
    private String newPassword;


}
