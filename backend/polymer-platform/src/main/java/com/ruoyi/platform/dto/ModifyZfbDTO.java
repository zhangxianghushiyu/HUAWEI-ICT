package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value="修改支付宝入参对象")
public class ModifyZfbDTO implements Serializable {

    @NotBlank(message = "支付宝姓名不能为空！")
    @ApiModelProperty(value="支付宝姓名")
    private String zfbName;

    @NotBlank(message = "支付宝账号不能为空！")
    @ApiModelProperty(value="支付宝账号")
    private String zfbUserName;


}
