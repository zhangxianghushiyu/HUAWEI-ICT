package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的报名记录返回对象
 */
@Data
@ApiModel(value = "我的报名记录返回对象")
public class MySignupListVO implements Serializable {

    @ApiModelProperty(value = "报名记录ID")
    private Long signupId;

    @ApiModelProperty(value = "活动ID")
    private Long activityId;

    @ApiModelProperty(value = "活动标题")
    private String activityTitle;

    @ApiModelProperty(value = "活动封面图")
    private String activityCoverImage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "活动开始时间")
    private Date activityStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "活动结束时间")
    private Date activityEndTime;

    @ApiModelProperty(value = "活动地点")
    private String activityLocation;

    @ApiModelProperty(value = "报名状态（0：已报名，1：已取消）")
    private Integer signupStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "报名时间")
    private Date signupTime;

    private static final long serialVersionUID = 1L;
}
