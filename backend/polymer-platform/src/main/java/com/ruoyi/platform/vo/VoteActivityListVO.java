package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "投票活动列表返回对象")
public class VoteActivityListVO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 投票标题
     */
    @ApiModelProperty(value = "投票标题")
    private String title;

    /**
     * 投票描述
     */
    @ApiModelProperty(value = "投票描述")
    private String description;

    /**
     * 封面图
     */
    @ApiModelProperty(value = "封面图")
    private String coverImage;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    /**
     * 状态（0：未开始，1：进行中，2：已结束）
     */
    @ApiModelProperty(value = "状态（0：未开始，1：进行中，2：已结束）")
    private Integer status;

    /**
     * 是否多选（0：单选，1：多选）
     */
    @ApiModelProperty(value = "是否多选（0：单选，1：多选）")
    private Integer isMultiple;

    /**
     * 总投票人数
     */
    @ApiModelProperty(value = "总投票人数")
    private Integer totalVoters;

    /**
     * 总投票数
     */
    @ApiModelProperty(value = "总投票数")
    private Integer totalVotes;

    /**
     * 是否已投票
     */
    @ApiModelProperty(value = "是否已投票")
    private Boolean hasVoted;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
