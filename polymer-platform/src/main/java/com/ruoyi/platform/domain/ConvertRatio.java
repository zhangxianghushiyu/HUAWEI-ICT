package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 兑换比例
 * t_convert_ratio
 */
@Data
public class ConvertRatio implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 比例
     */
    private Integer ratio;

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