package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "今日推荐返回对象")
public class EbookRecommendListVO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
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


}