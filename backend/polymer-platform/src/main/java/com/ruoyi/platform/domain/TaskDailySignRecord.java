package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 每日七日签到记录
 * t_task_daily_sign_record
 */
@Data
public class TaskDailySignRecord implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 批号（7日一循环）
     */
    private String batchNo;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 新人签到ID
     */
    private Integer dailySignId;

    /**
     * 获得金币
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