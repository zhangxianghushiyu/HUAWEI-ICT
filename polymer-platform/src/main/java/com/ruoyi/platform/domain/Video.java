package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频信息
 */
@Data
public class Video implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 视频名称
     */
    private String name;
    /**
     *  播放量
     */
    private Integer playCount;
    /**
     * 点赞量
     */
    private Integer likeCount;
    /**
     * 收藏量
     */
    private Integer collectCount;
    /**
     * 内容
     */
    private String content;
    /**
     * 视频地址
     */
    private String videoUrl;
    /**
     * 封面图
     */
    private String videoCover;
    /**
     * 视频状态，0下架，1正常
     */
    private Integer status;
    /**
     * 状态（0：删除，1：正常）
     */
        private Integer delStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    private Integer dramaId;





}
