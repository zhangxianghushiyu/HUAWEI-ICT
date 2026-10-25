package com.ruoyi.platform.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value="获取商品入参对象")
public class GoodsGetDTO implements Serializable {

    @NotNull(message = "商品ID不能为空！")
    @ApiModelProperty(value="商品ID")
    private Integer goodsId;

    @NotNull(message = "购买数量不能为空！")
    @ApiModelProperty(value="购买数量")
    private Integer count;

    @NotNull(message = "收货地址ID不能为空！")
    @ApiModelProperty(value="收货地址ID")
    private Integer addressId;
}
