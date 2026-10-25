package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "风控管理返回对象")
public class DetectionVO implements Serializable {

    @ApiModelProperty(value = "ROOT检测是否开启")
    private Integer isRoot;

    @ApiModelProperty(value = "模拟器检测是否开启")
    private Integer isSimulator;
}
