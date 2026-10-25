package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * video
 * @author XXX
 */
@Data
@ApiModel(value="视频详细返回对象")
public class VideoListVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 视频名称
     */
    @ApiModelProperty(value = "视频名称")
    private String name;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;
    /**
     * 视频地址
     */
    @ApiModelProperty(value = "视频地址")
    private String videoUrl;
    /**
     * 封面地址
     */
    @ApiModelProperty(value = "封面地址")
    private String videoCover;
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
     * 收藏量
     */
    @ApiModelProperty(value = "收藏量")
    private Integer collectCount;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;



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

    private Integer dramaId;

}
