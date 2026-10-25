package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 宝箱配置
 * t_treasure_box
 */
@Data
public class TreasureBox implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 金币
     */
    private BigDecimal price;

    /**
     * 分钟
     */
    private Integer minute;

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