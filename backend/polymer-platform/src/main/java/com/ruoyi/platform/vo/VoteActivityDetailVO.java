package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "投票活动详情返回对象")
public class VoteActivityDetailVO implements Serializable {

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
     * 最大可选数（多选时有效）
     */
    @ApiModelProperty(value = "最大可选数（多选时有效）")
    private Integer maxOptions;

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
     * 选项列表
     */
    @ApiModelProperty(value = "选项列表")
    private List<VoteOptionVO> options;

    /**
     * 用户已投票的选项ID列表
     */
    @ApiModelProperty(value = "用户已投票的选项ID列表")
    private List<Integer> userVotedOptionIds;

    /**
     * 是否已投票
     */
    @ApiModelProperty(value = "是否已投票")
    private Boolean hasVoted;

    private static final long serialVersionUID = 1L;
}
