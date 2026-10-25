package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 看指定剧领金币配置信息
 * t_task_daily_appoint_drama_coin_info
 */
@Data
public class TaskDailyAppointDramaCoinInfo implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 秒信息
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