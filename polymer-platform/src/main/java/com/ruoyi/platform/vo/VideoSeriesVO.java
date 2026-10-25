package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 视频系列返回对象
 */
@Data
@ApiModel(value = "视频系列返回对象")
public class VideoSeriesVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系列名")
    private String seriesName;

    @ApiModelProperty(value = "封面地址")
    private String cover;

    @ApiModelProperty(value = "集数")
    private Integer episodeCount;

    @ApiModelProperty(value = "总播放量")
    private Integer playCount;
}
