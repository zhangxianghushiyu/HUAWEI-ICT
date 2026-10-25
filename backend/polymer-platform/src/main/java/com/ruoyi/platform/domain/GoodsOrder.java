package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商城订单
 * t_goods_order
 */
@Data
public class GoodsOrder implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品价格（单价）
     */
    private BigDecimal goodsPrice;

    /**
     * 商品图片
     */
    private String goodsUrl;

    /**
     * 支付金额（金币）
     */
    private BigDecimal payMoney;

    /**
     * 购买数量
     */
    private Integer payCount;

    /**
     * 0：待发货，1：已发货，2：已收货
     */
    private Integer status;

    /**
     * 收货人姓名
     */
    private String getName;

    /**
     * 收货人手机号
     */
    private String getPhone;

    /**
     * 收货人城市
     */
    private String getCity;

    /**
     * 收货人地址
     */
    private String getAddress;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}