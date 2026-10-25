package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "今日推荐返回对象")
public class EbookRecommendLikeListVO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 字数
     */
    @ApiModelProperty(value = "字数")
    private Long words;

    /**
     * 封面图
     */
    @ApiModelProperty(value = "封面图")
    private String url;

    /**
     * 分数
     */
    @ApiModelProperty(value = "分数")
    private Double fraction;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 1：连载，2：完结
     */
    @ApiModelProperty(value = "1：连载，2：完结")
    private Integer isEnd;


    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;

    /**
     * 介绍
     */
    @ApiModelProperty(value = "介绍")
    private String introduce;

    /**
     * 分类字符串
     */
    @ApiModelProperty(value = "分类字符串")
    private List<String> categoryStr;


}