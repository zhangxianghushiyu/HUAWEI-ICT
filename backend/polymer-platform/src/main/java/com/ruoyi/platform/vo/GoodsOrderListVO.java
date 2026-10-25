package com.ruoyi.platform.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "商品订单集合返回对象")
public class GoodsOrderListVO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;


    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 商品价格（单价）
     */
    @ApiModelProperty(value = "商品价格（单价）")
    private BigDecimal goodsPrice;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片")
    private String goodsUrl;

    /**
     * 支付金额（金币）
     */
    @ApiModelProperty(value = "支付金额（金币）")
    private BigDecimal payMoney;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量")
    private Integer payCount;

    /**
     * 0：待发货，1：已发货，2：已收货
     */
    @ApiModelProperty(value = "0：待发货，1：已发货，2：已收货")
    private Integer status;

}
