package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 短剧解锁配置
 * t_drama_unlock
 */
@Data
public class DramaUnlock implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 免费集数
     */
    private Integer freeNum;

    /**
     * 解锁集数
     */
    private Integer unlockNum;

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