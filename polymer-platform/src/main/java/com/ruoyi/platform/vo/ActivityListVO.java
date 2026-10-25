package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动列表返回对象
 */
@Data
@ApiModel(value = "活动列表返回对象")
public class ActivityListVO implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "活动标题")
    private String title;

    @ApiModelProperty(value = "活动描述")
    private String description;

    @ApiModelProperty(value = "封面图")
    private String coverImage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "状态（0：未开始，1：进行中，2：已结束）")
    private Integer status;

    @ApiModelProperty(value = "活动地点")
    private String location;

    @ApiModelProperty(value = "主办方")
    private String organizer;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "是否已报名")
    private Boolean hasSignedUp;

    @ApiModelProperty(value = "报名人数")
    private Integer signupCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
