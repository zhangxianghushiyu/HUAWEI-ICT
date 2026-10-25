package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 投票活动
 */
@Data
public class VoteActivity implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 投票标题
     */
    private String title;

    /**
     * 投票描述
     */
    private String description;

    /**
     * 封面图
     */
    private String coverImage;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 状态（0：未开始，1：进行中，2：已结束）
     */
    private Integer status;

    /**
     * 是否多选（0：单选，1：多选）
     */
    private Integer isMultiple;

    /**
     * 最大可选数（多选时有效）
     */
    private Integer maxOptions;

    /**
     * 总投票人数
     */
    private Integer totalVoters;

    /**
     * 总投票数
     */
    private Integer totalVotes;

    /**
     * 删除状态（0：删除，1：正常）
     */
    private Integer delStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
