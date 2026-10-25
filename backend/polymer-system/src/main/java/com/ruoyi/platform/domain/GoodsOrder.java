package com.ruoyi.platform.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 商城订单对象 t_goods_order
 * 
 * @author ruoyi
 * @date 2025-03-18
 */
public class GoodsOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String goodsName;

    /** 商品价格（单价） */
    @Excel(name = "商品价格", readConverterExp = "单=价")
    private BigDecimal goodsPrice;

    /** 商品图片 */
    @Excel(name = "商品图片")
    private String goodsUrl;

    /** 支付金额（金币） */
    @Excel(name = "支付金额", readConverterExp = "金=叶")
    private BigDecimal payMoney;

    /** 购买数量 */
    @Excel(name = "购买数量")
    private Long payCount;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 收货人姓名 */
    @Excel(name = "收货人姓名")
    private String getName;

    /** 收货人手机号 */
    @Excel(name = "收货人手机号")
    private String getPhone;

    /** 收货人城市 */
    @Excel(name = "收货人城市")
    private String getCity;

    /** 收货人地址 */
    @Excel(name = "收货人地址")
    private String getAddress;

    private String userName;
    private String nickName;
    private String avatar;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }
    public void setGoodsName(String goodsName) 
    {
        this.goodsName = goodsName;
    }

    public String getGoodsName() 
    {
        return goodsName;
    }
    public void setGoodsPrice(BigDecimal goodsPrice) 
    {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsPrice() 
    {
        return goodsPrice;
    }
    public void setGoodsUrl(String goodsUrl) 
    {
        this.goodsUrl = goodsUrl;
    }

    public String getGoodsUrl() 
    {
        return goodsUrl;
    }
    public void setPayMoney(BigDecimal payMoney) 
    {
        this.payMoney = payMoney;
    }

    public BigDecimal getPayMoney() 
    {
        return payMoney;
    }
    public void setPayCount(Long payCount) 
    {
        this.payCount = payCount;
    }

    public Long getPayCount() 
    {
        return payCount;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setGetName(String getName) 
    {
        this.getName = getName;
    }

    public String getGetName() 
    {
        return getName;
    }
    public void setGetPhone(String getPhone) 
    {
        this.getPhone = getPhone;
    }

    public String getGetPhone() 
    {
        return getPhone;
    }
    public void setGetCity(String getCity) 
    {
        this.getCity = getCity;
    }

    public String getGetCity() 
    {
        return getCity;
    }
    public void setGetAddress(String getAddress) 
    {
        this.getAddress = getAddress;
    }

    public String getGetAddress() 
    {
        return getAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("orderNo", getOrderNo())
            .append("goodsName", getGoodsName())
            .append("goodsPrice", getGoodsPrice())
            .append("goodsUrl", getGoodsUrl())
            .append("payMoney", getPayMoney())
            .append("payCount", getPayCount())
            .append("status", getStatus())
            .append("getName", getGetName())
            .append("getPhone", getGetPhone())
            .append("getCity", getGetCity())
            .append("getAddress", getGetAddress())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
