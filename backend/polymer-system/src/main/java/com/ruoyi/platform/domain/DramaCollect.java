package com.ruoyi.platform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 短剧收藏对象 t_drama_collect
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
public class DramaCollect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 短剧ID */
    @Excel(name = "短剧ID")
    private Long dramaId;

    /** 短剧详细ID */
    @Excel(name = "短剧详细ID")
    private Long dramaDetailsId;

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
    public void setDramaId(Long dramaId) 
    {
        this.dramaId = dramaId;
    }

    public Long getDramaId() 
    {
        return dramaId;
    }
    public void setDramaDetailsId(Long dramaDetailsId) 
    {
        this.dramaDetailsId = dramaDetailsId;
    }

    public Long getDramaDetailsId() 
    {
        return dramaDetailsId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("dramaId", getDramaId())
            .append("dramaDetailsId", getDramaDetailsId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
