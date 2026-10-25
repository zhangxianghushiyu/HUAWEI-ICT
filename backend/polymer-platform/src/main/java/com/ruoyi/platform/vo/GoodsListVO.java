package com.ruoyi.platform.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "商品集合返回对象")
public class GoodsListVO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;

    /**
     * 金币价格
     */
    @ApiModelProperty(value = "金币价格")
    private BigDecimal leafPrice;

    /**
     * 商品图
     */
    @ApiModelProperty(value = "商品图")
    private String url;
}
