package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 新人看剧送现金配置
 * t_task_new_people_look_drama
 */
@Data
public class TaskNewPeopleLookDrama implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 排序（定义为天）
     */
    private Integer sort;

    /**
     * 现金
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