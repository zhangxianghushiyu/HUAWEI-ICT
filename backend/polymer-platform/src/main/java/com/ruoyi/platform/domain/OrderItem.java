package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 订单详细信息
 * t_order_item
 */
@Data
public class OrderItem implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单类型（1：会员）
     */
    private Integer orderType;

    /**
     * 1：月会员，2：季会员，3：半年会员，4：年会员，5：永久
     */
    private Integer vipType;

    /**
     * VIP价格
     */
    private BigDecimal vipPrice;

    /**
     * VIP天数
     */
    private Integer vipDay;

    /**
     * 支付状态（1：未支付，2：已支付，3：已退款）
     */
    private Integer payStatus;

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