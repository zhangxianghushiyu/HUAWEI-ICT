package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 饭点看剧领金币配置
 * t_task_daily_food_point_look_drama
 */
@Data
public class TaskDailyFoodPointLookDrama implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

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