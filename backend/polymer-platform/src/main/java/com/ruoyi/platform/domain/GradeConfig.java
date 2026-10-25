package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户阶级配置
 * t_grade_config
 */
@Data
public class GradeConfig implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 等级
     */
    private Integer grade;

    /**
     * 关卡数
     */
    private Integer barrier;

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