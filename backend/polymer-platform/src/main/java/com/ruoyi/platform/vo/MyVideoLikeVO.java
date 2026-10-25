package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "我的视频点赞")
public class MyVideoLikeVO {
    @ApiModelProperty(value = "视频id")
    private Integer videoId;
    @ApiModelProperty("视频标题")
    private String name;
    @ApiModelProperty("封面")
    private String videoCover;
}
