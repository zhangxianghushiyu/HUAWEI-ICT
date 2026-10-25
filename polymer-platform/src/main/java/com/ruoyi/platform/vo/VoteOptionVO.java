package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "投票选项返回对象")
public class VoteOptionVO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 选项内容
     */
    @ApiModelProperty(value = "选项内容")
    private String optionContent;

    /**
     * 选项图片
     */
    @ApiModelProperty(value = "选项图片")
    private String optionImage;

    /**
     * 票数
     */
    @ApiModelProperty(value = "票数")
    private Integer voteCount;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 票数百分比
     */
    @ApiModelProperty(value = "票数百分比")
    private BigDecimal percentage;

    /**
     * 用户是否已选
     */
    @ApiModelProperty(value = "用户是否已选")
    private Boolean userSelected;

    private static final long serialVersionUID = 1L;
}
