package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "我的视频收藏返回对象")
public class MyVideoCollectVO implements Serializable {

    @ApiModelProperty(value = "视频id")
    private Integer videoId;
    @ApiModelProperty(value = "视频标题")
    private String name;
    @ApiModelProperty(value = "封面")
    private String videoCover;
}
