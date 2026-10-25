package com.ruoyi.platform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 投票选项对象 t_vote_option
 * 
 * @author lhr
 * @date 2026-05-15
 */
public class VoteOption extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 投票活动ID */
    @Excel(name = "投票活动ID")
    private Long activityId;

    /** 选项内容 */
    @Excel(name = "选项内容")
    private String optionContent;

    /** 选项图片 */
    @Excel(name = "选项图片")
    private String optionImage;

    /** 票数 */
    @Excel(name = "票数")
    private Long voteCount;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 删除状态（0：删除，1：正常） */
    @Excel(name = "删除状态", readConverterExp = "0=：删除，1：正常")
    private Long delStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setActivityId(Long activityId) 
    {
        this.activityId = activityId;
    }

    public Long getActivityId() 
    {
        return activityId;
    }
    public void setOptionContent(String optionContent) 
    {
        this.optionContent = optionContent;
    }

    public String getOptionContent() 
    {
        return optionContent;
    }
    public void setOptionImage(String optionImage) 
    {
        this.optionImage = optionImage;
    }

    public String getOptionImage() 
    {
        return optionImage;
    }
    public void setVoteCount(Long voteCount) 
    {
        this.voteCount = voteCount;
    }

    public Long getVoteCount() 
    {
        return voteCount;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setDelStatus(Long delStatus) 
    {
        this.delStatus = delStatus;
    }

    public Long getDelStatus() 
    {
        return delStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activityId", getActivityId())
            .append("optionContent", getOptionContent())
            .append("optionImage", getOptionImage())
            .append("voteCount", getVoteCount())
            .append("sort", getSort())
            .append("delStatus", getDelStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
