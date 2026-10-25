package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "我的收藏返回对象")
public class MyDramaCollectVO implements Serializable {

    /**
     * 短剧ID
     */
    @ApiModelProperty(value = "短剧ID")
    private Integer dramaId;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 封面图
     */
    @ApiModelProperty(value = "封面图")
    private String titleImg;


    /**
     * 0：不推荐，1：已推荐
     */
    @ApiModelProperty(value = "0：不推荐，1：已推荐")
    private Integer isRecommend;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    private Integer classifyId;

    /**
     * 短剧介绍
     */
    @ApiModelProperty(value = "短剧介绍")
    private String details;


    /**
     * 播放量
     */
    @ApiModelProperty(value = "播放量")
    private Integer playCount;

    /**
     * 点赞量
     */
    @ApiModelProperty(value = "点赞量")
    private Integer likeCount;

    /**
     * 是否完结（0：未完结，1：已完结）
     */
    @ApiModelProperty(value = "是否完结（0：未完结，1：已完结）")
    private Integer isEnd;

    /**
     * 观看集数
     */
    @ApiModelProperty(value = "观看集ID")
    private Integer watchGather = 1;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    /**
     * 是否收费（0：免费，1：收费）
     */
    @ApiModelProperty(value = "是否收费（0：免费，1：收费）")
    private Integer isPrice;

    /**
     * 是否收藏
     */
    @ApiModelProperty(value = "是否收藏（0：未收藏，1：已收藏）")
    private Integer isCollect = 0;

}
