package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息
 * t_goods
 */
@Data
public class Goods implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    private Integer goodsType;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 金币价格
     */
    private BigDecimal leafPrice;

    /**
     * 0：下架，1：上架
     */
    private Integer upStatus;

    /**
     * 商品图
     */
    private String url;

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