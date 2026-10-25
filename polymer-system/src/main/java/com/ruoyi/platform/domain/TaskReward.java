package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务对象 t_task_reward
 * 
 * @author ruoyi
 * @date 2024-11-19
 */
public class TaskReward extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 类型 */
    @Excel(name = "类型")
    private Integer type;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 副类型 */
    @Excel(name = "副类型")
    private Integer followType;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 描述 */
    @Excel(name = "描述")
    private String describe;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 奖励类型 */
    @Excel(name = "奖励类型")
    private Integer rewardType;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal price;

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
    public void setFollowType(Integer followType) 
    {
        this.followType = followType;
    }

    public Integer getFollowType() 
    {
        return followType;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setDescribe(String describe) 
    {
        this.describe = describe;
    }

    public String getDescribe() 
    {
        return describe;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setRewardType(Integer rewardType) 
    {
        this.rewardType = rewardType;
    }

    public Integer getRewardType() 
    {
        return rewardType;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("followType", getFollowType())
            .append("title", getTitle())
            .append("describe", getDescribe())
            .append("sort", getSort())
            .append("rewardType", getRewardType())
            .append("price", getPrice())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
