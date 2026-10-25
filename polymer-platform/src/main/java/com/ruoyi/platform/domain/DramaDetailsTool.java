package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 短剧详细
 * t_drama_details_tool
 */
@Data
public class DramaDetailsTool implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 短剧ID
     */
    private Integer dramaId;

    /**
     * 短剧名称
     */
    private String name;

    /**
     * 短剧地址
     */
    private String videoUrl;

    /**
     * 封面图
     */
    private String titleImg;

    /**
     * 介绍
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 播放量
     */
    private Integer playCount;

    /**
     * 收藏量
     */
    private Integer collectCount;

    /**
     * 广告次数
     */
    private Integer publicizeCount;

    /**
     * 0：删除，1：正常
     */
    private Integer delStatus;

    /**
     * 0：下架，1：上架
     */
    private Integer status;

    /**
     * 当前集数
     */
    private Integer gather;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 是否收费（0：免费，1：收费）
     */
    private Integer isPrice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}