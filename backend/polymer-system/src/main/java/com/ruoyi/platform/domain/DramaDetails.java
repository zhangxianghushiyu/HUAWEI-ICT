package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 短剧详细对象 t_drama_details
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
public class DramaDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 短剧ID */
    @Excel(name = "短剧ID")
    private Long dramaId;

    /** 短剧名称 */
    @Excel(name = "短剧名称")
    private String name;

    /** 短剧地址 */
    @Excel(name = "短剧地址")
    private String videoUrl;

    /** 封面图 */
    @Excel(name = "封面图")
    private String titleImg;

    /** 介绍 */
    @Excel(name = "介绍")
    private String content;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Integer likeCount;

    /** 播放量 */
    @Excel(name = "播放量")
    private Integer playCount;

    /** 收藏量 */
    @Excel(name = "收藏量")
    private Integer collectCount;

    /** 删除状态 */
    @Excel(name = "删除状态")
    private Integer delStatus;

    /** 是否上架 */
    @Excel(name = "是否上架")
    private Integer status;

    /** 当前集数 */
    @Excel(name = "当前集数")
    private Integer gather;

    /** 是否收费（0：免费，1：收费） */
    @Excel(name = "是否收费", readConverterExp = "0=：免费，1：收费")
    private Integer isPrice;

    /** 视频宽度 */
    private Integer videoWidth;

    /** 视频高度 */
    private Integer videoHeight;


    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }


    public Integer getGather() {
        return gather;
    }

    public void setGather(Integer gather) {
        this.gather = gather;
    }

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setVideoUrl(String videoUrl) 
    {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() 
    {
        return videoUrl;
    }
    public void setTitleImg(String titleImg) 
    {
        this.titleImg = titleImg;
    }

    public String getTitleImg() 
    {
        return titleImg;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setLikeCount(Integer likeCount) 
    {
        this.likeCount = likeCount;
    }

    public Integer getLikeCount() 
    {
        return likeCount;
    }
    public void setPlayCount(Integer playCount) 
    {
        this.playCount = playCount;
    }

    public Integer getPlayCount() 
    {
        return playCount;
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
    public void setIsPrice(Integer isPrice) 
    {
        this.isPrice = isPrice;
    }

    public Integer getIsPrice()
    {
        return isPrice;
    }

    public Integer getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(Integer videoWidth) {
        this.videoWidth = videoWidth;
    }

    public Integer getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dramaId", getDramaId())
            .append("name", getName())
            .append("videoUrl", getVideoUrl())
            .append("titleImg", getTitleImg())
            .append("content", getContent())
            .append("likeCount", getLikeCount())
            .append("playCount", getPlayCount())
            .append("delStatus", getDelStatus())
            .append("status", getStatus())
            .append("isPrice", getIsPrice())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
