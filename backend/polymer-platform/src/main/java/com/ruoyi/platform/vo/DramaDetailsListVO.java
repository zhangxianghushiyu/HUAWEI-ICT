package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@ApiModel(value="短剧详细信息返回对象")
public class DramaDetailsListVO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value="短剧详细主键")
    private Integer dramaDetailsId;

    /**
     * 短剧ID
     */
    @ApiModelProperty(value="短剧ID")
    private Integer dramaId;

    /**
     * 短剧名称
     */
    @ApiModelProperty(value="短剧名称")
    private String name;

    /**
     * 短剧地址
     */
    @ApiModelProperty(value="短剧地址")
    private String videoUrl;

    /**
     * 封面图
     */
    @ApiModelProperty(value="封面图")
    private String titleImg;

    /**
     * 介绍
     */
    @ApiModelProperty(value="介绍")
    private String content;

    /**
     * 点赞数
     */
    @ApiModelProperty(value="点赞数")
    private Integer likeCount;

    /**
     * 当前集数
     */
    @ApiModelProperty(value="当前集数")
    private Integer gather;



    /**
     * 是否收费
     */
    @ApiModelProperty(value="是否收费(0：免费，1：收费)")
    private Integer isPrice;


    /**
     * 是否收藏
     */
    @ApiModelProperty(value = "是否收藏（0：未收藏，1：已收藏）")
    private Integer isCollect = 0;

    /**
     * 是否点赞
     */
    @ApiModelProperty(value = "是否点赞（0：未点赞，1：已点赞）")
    private Integer isLike = 0;


    /**
     * 0：普通短剧，1：vip专享
     */
    @ApiModelProperty(value = "0：普通短剧，1：vip专享")
    private Integer isVip;

    /**
     * 收藏量
     */
    @ApiModelProperty(value = "收藏量")
    private Integer collectCount;

    /**
     * 一个广告解锁的集数
     */
    @ApiModelProperty(value = "一个广告解锁的集数")
    private Integer unlockCount;
    /**
     * 是否完结（0：未完结，1：已完结）
     */
    @ApiModelProperty(value = "是否完结（0：未完结，1：已完结）")
    private Integer isEnd;

    /**
     * 视频宽度
     */
    @ApiModelProperty(value = "视频宽度")
    private Integer videoWidth;

    /**
     * 视频高度
     */
    @ApiModelProperty(value = "视频高度")
    private Integer videoHeight;
}
