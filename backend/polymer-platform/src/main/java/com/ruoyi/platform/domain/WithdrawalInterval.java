package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 提现间隔配置
 * t_withdrawal_interval
 */
@Data
public class WithdrawalInterval implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 天
     */
    private Integer day;

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