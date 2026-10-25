package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动表
 * t_task_reward
 */
@Data
public class TaskReward implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 任务类型(1:新手任务,2:日常任务)
     */
    private Integer type;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 任务副类型（1：见面领现金，2：新人签到领金币，3：提现一元，4：新人看剧送现金，5：每日签到，6：饭点看剧领补贴，7：看剧领金币，8：看小视频领金币，9：分享领金币，10：看下方剧领金币）
     */
    private Integer followType;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String describe;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 奖励类型（1：金币，2：现金，3：人民币）
     */
    private Integer rewardType;

    /**
     * 金额
     */
    private BigDecimal price;

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