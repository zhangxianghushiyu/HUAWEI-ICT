package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 看视频领金币配置
 * t_task_daily_look_video_to_coin
 */
@Data
public class TaskDailyLookVideoToCoin implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 次数
     */
    private Integer count;

    private Integer secondInfo;

    /**
     * 金币
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

    /**
     * 间隔时间
     */
    private Integer interval;

    private static final long serialVersionUID = 1L;
}
