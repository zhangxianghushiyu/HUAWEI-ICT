package com.ruoyi.platform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 看指定剧领金币配置对象 t_task_daily_appoint_drama_coin
 * 
 * @author ruoyi
 * @date 2024-11-25
 */
public class TaskDailyAppointDramaCoin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 剧ID */
    @Excel(name = "剧ID")
    private Long dramaId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDramaId(Long dramaId) 
    {
        this.dramaId = dramaId;
    }

    public Long getDramaId() 
    {
        return dramaId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dramaId", getDramaId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
