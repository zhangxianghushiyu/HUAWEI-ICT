package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 新人看剧领现金记录
 * t_task_new_people_look_drama_record
 */
@Data
public class TaskNewPeopleLookDramaRecord implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 新人看剧配置ID
     */
    private Integer newPeopleLookDramaId;

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