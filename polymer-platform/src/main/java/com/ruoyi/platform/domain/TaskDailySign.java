package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 每日七日签到配置
 * t_task_daily_sign
 */
@Data
public class TaskDailySign implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 排序（定义为天）
     */
    private Integer sort;

    /**
     * 金币数量
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