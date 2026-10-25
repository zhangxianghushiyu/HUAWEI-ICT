package com.ruoyi.platform.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 视频对象 t_video
 *
 * @author ruoyi
 * @date 2024-11-11
 */
public class VideoListVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 视频名称 */
    @Excel(name = "视频名称")
    private String name;

    /** 播放量 */
//    @Excel(name = "播放量")
    private Integer playCount;

    /** 点赞量 */
    @Excel(name = "点赞量")
    private Integer likeCount;

    /** 收藏量 */
    @Excel(name = "收藏量")
    private Integer collectCount;

    /** 视频简介 */
    @Excel(name = "视频简介")
    private String content;

    /** 视频路径 */
    @Excel(name = "视频路径")
    private String videoUrl;

    /** 视频封面 */
    @Excel(name = "视频封面")
    private String videoCover;

    /** 是否上架 */
//    @Excel(name = "是否上架")
    private Integer status;

    /** 删除状态 */
//    @Excel(name = "删除状态")
    private Integer delStatus;

    private Integer dramaId;

    /** 视频分类ID */
    private Integer classifyId;

    /** 系列名 */
    private String seriesName;

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
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
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setVideoUrl(String videoUrl)
    {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl()
    {
        return videoUrl;
    }
    public void setVideoCover(String videoCover)
    {
        this.videoCover = videoCover;
    }

    public String getVideoCover()
    {
        return videoCover;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setDelStatus(Integer delStatus)
    {
        this.delStatus = delStatus;
    }

    public Integer getDelStatus()
    {
        return delStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("playCount", getPlayCount())
            .append("likeCount", getLikeCount())
            .append("content", getContent())
            .append("videoUrl", getVideoUrl())
            .append("videoCover", getVideoCover())
            .append("status", getStatus())
            .append("delStatus", getDelStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public Integer getDramaId() {
        return dramaId;
    }

    public void setDramaId(Integer dramaId) {
        this.dramaId = dramaId;
    }

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}
