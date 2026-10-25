package com.ruoyi.platform.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 商品信息对象 t_goods
 * 
 * @author ruoyi
 * @date 2025-03-14
 */
@Data
public class Goods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    private Integer goodsType;
    private String typeName;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String name;

    /** 金币价格 */
    @Excel(name = "金币价格")
    private BigDecimal leafPrice;

    /** 状态 */
    @Excel(name = "状态")
    private Integer upStatus;

    /** 商品图 */
    @Excel(name = "商品图")
    private String url;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setLeafPrice(BigDecimal leafPrice) 
    {
        this.leafPrice = leafPrice;
    }

    public BigDecimal getLeafPrice() 
    {
        return leafPrice;
    }
    public void setUpStatus(Integer upStatus) 
    {
        this.upStatus = upStatus;
    }

    public Integer getUpStatus() 
    {
        return upStatus;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("leafPrice", getLeafPrice())
            .append("upStatus", getUpStatus())
            .append("url", getUrl())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
