package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 新人七日签到记录
 * t_task_new_people_sign_record
 */
@Data
public class TaskNewPeopleSignRecord implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 新人签到ID
     */
    private Integer newPeopleSignId;

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