package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务完成记录
 * t_task_complete_record
 */
@Data
public class TaskCompleteRecord implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 任务ID
     */
    private Integer taskRewardId;

    /**
     * 任务标题
     */
    private String taskTitle;

    /**
     * 任务描述
     */
    private String taskDescribe;

    /**
     * 任务类型(1:新手任务,2:日常任务)
     */
    private Integer taskType;

    /**
     * 任务副类型（1：见面领现金，2：新人签到领金币，3：提现一元，4：新人看剧送现金，5：每日签到，6：饭点看剧领补贴，7：看剧领金币，8：看小视频领金币，9：分享领金币，10：看下方剧领金币）
     */
    private Integer taskFollowType;

    /**
     * 奖励类型（1：金币，2：现金，3：人民币）
     */
    private Integer taskRewardType;

    /**
     * 金额
     */
    private BigDecimal taskPrice;

    /**
     * 描述
     */
    private String describe;

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