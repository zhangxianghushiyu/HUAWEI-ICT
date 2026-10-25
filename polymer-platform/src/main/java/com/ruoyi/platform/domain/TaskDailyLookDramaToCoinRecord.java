package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 看剧领金币任务记录
 * t_task_daily_look_drama_to_coin_record
 */
@Data
public class TaskDailyLookDramaToCoinRecord implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 观看时间（分钟）
     */
    private Integer secondInfo;

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