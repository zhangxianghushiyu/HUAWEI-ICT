package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 视频系列选集返回对象
 */
@Data
@ApiModel(value = "视频系列选集返回对象")
public class VideoSeriesEpisodeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "视频名称")
    private String name;

    @ApiModelProperty(value = "视频地址")
    private String videoUrl;

    @ApiModelProperty(value = "封面地址")
    private String videoCover;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "播放量")
    private Integer playCount;

    @ApiModelProperty(value = "集数")
    private Integer episodeNum;

    @ApiModelProperty(value = "是否免费（0否1是）")
    private Integer isFree = 0;

    @ApiModelProperty(value = "是否已解锁（0否1是）")
    private Integer isUnlocked = 0;

    @ApiModelProperty(value = "是否锁定（0否1是）")
    private Integer isLocked = 0;

    @ApiModelProperty(value = "金币解锁单价")
    private Integer unlockGoldCost;

    @ApiModelProperty(value = "所属系列名")
    private String seriesName;
}
