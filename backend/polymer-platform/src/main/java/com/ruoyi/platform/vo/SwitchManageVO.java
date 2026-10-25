package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="开关信息返回对象")
public class SwitchManageVO implements Serializable {


    @ApiModelProperty(value="类型（1：打包环境开关）")
    private Integer type;


    @ApiModelProperty(value="状态（0：审核包，1：交付包）")
    private Integer status;
}
