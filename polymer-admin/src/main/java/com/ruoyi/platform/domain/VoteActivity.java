package com.ruoyi.platform.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 投票活动对象 t_vote_activity
 * 
 * @author lhr
 * @date 2026-05-15
 */
public class VoteActivity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 投票标题 */
    @Excel(name = "投票标题")
    private String title;

    /** 投票描述 */
    @Excel(name = "投票描述")
    private String description;

    /** 封面图 */
    @Excel(name = "封面图")
    private String coverImage;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 状态（0：未开始，1：进行中，2：已结束） */
    @Excel(name = "状态", readConverterExp = "0=：未开始，1：进行中，2：已结束")
    private Long status;

    /** 是否多选（0：单选，1：多选） */
    @Excel(name = "是否多选", readConverterExp = "0=：单选，1：多选")
    private Long isMultiple;

    /** 最大可选数（多选时有效） */
    @Excel(name = "最大可选数", readConverterExp = "多=选时有效")
    private Long maxOptions;

    /** 总投票人数 */
    @Excel(name = "总投票人数")
    private Long totalVoters;

    /** 总投票数 */
    @Excel(name = "总投票数")
    private Long totalVotes;

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
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setCoverImage(String coverImage) 
    {
        this.coverImage = coverImage;
    }

    public String getCoverImage() 
    {
        return coverImage;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setIsMultiple(Long isMultiple) 
    {
        this.isMultiple = isMultiple;
    }

    public Long getIsMultiple() 
    {
        return isMultiple;
    }
    public void setMaxOptions(Long maxOptions) 
    {
        this.maxOptions = maxOptions;
    }

    public Long getMaxOptions() 
    {
        return maxOptions;
    }
    public void setTotalVoters(Long totalVoters) 
    {
        this.totalVoters = totalVoters;
    }

    public Long getTotalVoters() 
    {
        return totalVoters;
    }
    public void setTotalVotes(Long totalVotes) 
    {
        this.totalVotes = totalVotes;
    }

    public Long getTotalVotes() 
    {
        return totalVotes;
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
            .append("title", getTitle())
            .append("description", getDescription())
            .append("coverImage", getCoverImage())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("status", getStatus())
            .append("isMultiple", getIsMultiple())
            .append("maxOptions", getMaxOptions())
            .append("totalVoters", getTotalVoters())
            .append("totalVotes", getTotalVotes())
            .append("delStatus", getDelStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
