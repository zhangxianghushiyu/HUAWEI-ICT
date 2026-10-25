package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 看指定剧领金币配置信息对象 t_task_daily_appoint_drama_coin_info
 * 
 * @author ruoyi
 * @date 2024-11-25
 */
public class TaskDailyAppointDramaCoinInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 秒信息 */
    @Excel(name = "秒信息")
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
