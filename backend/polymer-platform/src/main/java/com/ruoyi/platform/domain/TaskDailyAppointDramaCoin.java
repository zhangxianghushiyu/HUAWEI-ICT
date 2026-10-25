package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 看指定剧领金币配置
 * t_task_daily_appoint_drama_coin
 */
@Data
public class TaskDailyAppointDramaCoin implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 剧ID
     */
    private Integer dramaId;

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