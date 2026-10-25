package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 看剧领金币配置
 * t_task_daily_look_drama_to_coin
 */
@Data
public class TaskDailyLookDramaToCoin implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 分钟
     */
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

    private static final long serialVersionUID = 1L;
}