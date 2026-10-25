package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 短剧信息对象 t_drama
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
public class Drama extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 封面图 */
    @Excel(name = "封面图")
    private String titleImg;

    /** 是否推荐 */
    @Excel(name = "是否推荐")
    private Integer isRecommend;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long classifyId;

    /** 短剧介绍 */
    @Excel(name = "短剧介绍")
    private String details;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer delStatus;

    /** 是否上架 */
    @Excel(name = "是否上架")
    private Integer status;

    /** 播放量 */
    @Excel(name = "播放量")
    private Integer playCount;

    /** 点赞量 */
    @Excel(name = "点赞量")
    private Integer likeCount;

    /** 是否收费（0：免费，1：收费） */
    @Excel(name = "是否收费", readConverterExp = "0=：免费，1：收费")
    private Integer isPrice;

    /** 是否完结（0：未完结，1：已完结） */
    @Excel(name = "是否完结（0：未完结，1：已完结）", readConverterExp = "0=：未完结，1：已完结")
    private Integer isEnd;

    /** 最近更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最近更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date upTime;


//分类名称
    private String className;

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

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
    public void setTitleImg(String titleImg) 
    {
        this.titleImg = titleImg;
    }

    public String getTitleImg() 
    {
        return titleImg;
    }
    public void setIsRecommend(Integer isRecommend) 
    {
        this.isRecommend = isRecommend;
    }

    public Integer getIsRecommend() 
    {
        return isRecommend;
    }
    public void setClassifyId(Long classifyId) 
    {
        this.classifyId = classifyId;
    }

    public Long getClassifyId() 
    {
        return classifyId;
    }
    public void setDetails(String details) 
    {
        this.details = details;
    }

    public String getDetails() 
    {
        return details;
    }
    public void setDelStatus(Integer delStatus) 
    {
        this.delStatus = delStatus;
    }

    public Integer getDelStatus() 
    {
        return delStatus;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setPlayCount(Integer playCount) 
    {
        this.playCount = playCount;
    }

    public Integer getPlayCount() 
    {
        return playCount;
    }
    public void setLikeCount(Integer likeCount) 
    {
        this.likeCount = likeCount;
    }

    public Integer getLikeCount() 
    {
        return likeCount;
    }
    public void setIsPrice(Integer isPrice) 
    {
        this.isPrice = isPrice;
    }

    public Integer getIsPrice() 
    {
        return isPrice;
    }
    public void setUpTime(Date upTime) 
    {
        this.upTime = upTime;
    }

    public Date getUpTime() 
    {
        return upTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("titleImg", getTitleImg())
            .append("isRecommend", getIsRecommend())
            .append("classifyId", getClassifyId())
            .append("details", getDetails())
            .append("delStatus", getDelStatus())
            .append("status", getStatus())
            .append("playCount", getPlayCount())
            .append("likeCount", getLikeCount())
            .append("isPrice", getIsPrice())
            .append("createTime", getCreateTime())
            .append("upTime", getUpTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
