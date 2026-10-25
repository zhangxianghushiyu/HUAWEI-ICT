package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 收益方式对象 t_income_way
 * 
 * @author ruoyi
 * @date 2025-05-15
 */
public class IncomeWay extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 1：ECPM，2：区间值 */
    @Excel(name = "1：ECPM，2：区间值")
    private Integer type;

    /** 金额最小 */
    @Excel(name = "金额最小")
    private BigDecimal moneyMin;

    /** 金额最大 */
    @Excel(name = "金额最大")
    private BigDecimal moneyMax;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }
    public void setMoneyMin(BigDecimal moneyMin) 
    {
        this.moneyMin = moneyMin;
    }

    public BigDecimal getMoneyMin() 
    {
        return moneyMin;
    }
    public void setMoneyMax(BigDecimal moneyMax) 
    {
        this.moneyMax = moneyMax;
    }

    public BigDecimal getMoneyMax() 
    {
        return moneyMax;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("moneyMin", getMoneyMin())
            .append("moneyMax", getMoneyMax())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
