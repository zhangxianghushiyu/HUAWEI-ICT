package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 订单信息
 * t_order
 */
@Data
public class Order implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 微信用户openId
     */
    private String wxOpenId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 1：微信，2：支付宝
     */
    private Integer payType;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;

    /**
     * 支付状态（1：未支付，2：已支付，3：已退款）
     */
    private Integer payStatus;

    /**
     * 订单类型（1：会员）
     */
    private Integer orderType;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 退款时间
     */
    private Date refundTime;

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