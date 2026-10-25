package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@ApiModel(value="微信创建订单入参对象")
public class WxCreateOrderDTO {

    @NotNull(message = "订单号不能为空！")
    @ApiModelProperty(value="订单号")
    private String orderNo;
}
