package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value="修改用户信息入参对象")
public class UserModifyDTO implements Serializable {

    @NotBlank(message = "头像不能为空！")
    @ApiModelProperty(value="头像")
    private String avatar;

    @NotBlank(message = "昵称不能为空！")
    @ApiModelProperty(value="昵称")
    private String nickName;

}
