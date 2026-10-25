package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 提现金额选项卡
 * t_withdrawal_tab
 */
@Data
public class WithdrawalTab implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 选项卡类型（1：微信，2：支付宝）
     */
    private Integer type;

    /**
     * 自动打款（0：否，1：是）
     */
    private Integer autoAccount;

    /**
     * 金额
     */
    private BigDecimal amount;

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