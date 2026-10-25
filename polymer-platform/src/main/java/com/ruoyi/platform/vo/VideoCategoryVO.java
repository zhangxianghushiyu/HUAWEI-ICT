package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 视频分类返回对象
 */
@Data
@ApiModel(value = "视频分类返回对象")
public class VideoCategoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类ID")
    private Integer classifyId;

    @ApiModelProperty(value = "名称")
    private String name;
}
