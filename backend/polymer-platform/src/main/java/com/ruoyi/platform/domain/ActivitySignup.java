package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报名记录对象
 */
@Data
public class ActivitySignup implements Serializable {
    
    /** 主键 */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 活动ID */
    private Long activityId;

    /** 报名状态（0：已报名，1：已取消） */
    private Long status;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
