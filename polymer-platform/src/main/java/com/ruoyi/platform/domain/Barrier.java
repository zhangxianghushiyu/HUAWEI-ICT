package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 关卡信息
 * t_barrier
 */
@Data
public class Barrier implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 关卡
     */
    private Integer barrier;

    /**
     * 数量
     */
    private Integer number;

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