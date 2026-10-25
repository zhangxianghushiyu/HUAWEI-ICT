package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "微信提示返回对象")
public class WxNeedReturnVO implements Serializable {

    @ApiModelProperty(value = "1:金额，2：关卡")
    private Integer type;

    @ApiModelProperty(value = "关卡")
    private Integer barrier;

    @ApiModelProperty(value = "金额")
    private BigDecimal money;

}