package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 短剧信息
 * t_drama_tool
 */
@Data
public class DramaTool implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面图
     */
    private String titleImg;

    /**
     * 0：普通短剧，1：vip专享
     */
    private Integer isVip;

    /**
     * 0：不推荐，1：已推荐
     */
    private Integer isRecommend;

    /**
     * 分类ID
     */
    private Integer classifyId;

    /**
     * 短剧介绍
     */
    private String details;

    /**
     * 0：删除，1：正常
     */
    private Integer delStatus;

    /**
     * 0：下架，1：上架
     */
    private Integer status;

    /**
     * 播放量
     */
    private Integer playCount;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 是否收费（0：免费，1：收费）
     */
    private Integer isPrice;

    /**
     * 是否完结（0：未完结，1：已完结）
     */
    private Integer isEnd;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近更新时间
     */
    private Date upTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}