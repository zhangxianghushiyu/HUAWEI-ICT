package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 视频系列解锁配置
 * t_video_series_unlock
 */
@Data
public class VideoSeriesUnlock implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 免费集数
     */
    private Integer freeNum;

    /**
     * 解锁集数（看一个广告解锁的集数）
     */
    private Integer unlockNum;

    /**
     * 金币解锁单价
     */
    private BigDecimal money;

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
