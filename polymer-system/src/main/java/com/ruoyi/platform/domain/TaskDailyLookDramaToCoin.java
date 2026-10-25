package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 看剧领金币配置对象 t_task_daily_look_drama_to_coin
 * 
 * @author ruoyi
 * @date 2024-11-22
 */
public class TaskDailyLookDramaToCoin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 秒 */
    @Excel(name = "秒")
    private Long secondInfo;

    /** 金币 */
    @Excel(name = "金币")
    private BigDecimal price;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSecondInfo(Long secondInfo) 
    {
        this.secondInfo = secondInfo;
    }

    public Long getSecondInfo() 
    {
        return secondInfo;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("secondInfo", getSecondInfo())
            .append("price", getPrice())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
