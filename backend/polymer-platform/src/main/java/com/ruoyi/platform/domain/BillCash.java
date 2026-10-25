package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 现金明细
 * t_bill_cash
 */
@Data
public class BillCash implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 提现方式（1：微信，2：支付宝）
     */
    private Integer method;

    /**
     * 类型（1：任务，2：提现）
     */
    private Integer type;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 提现失败原因
     */
    private String failureReason;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 场景（1：加，2：减）
     */
    private Integer scene;

    /**
     * 描述
     */
    private String describe;

    /**
     * 微信提现返回信息
     */
    private String packageInfo;

    /**
     * 选项卡金额
     */
    private BigDecimal money;

    /**
     * 状态（0：审核中，1：已打款，2：已驳回，3：已通过）
     */
    private Integer status;

    /**
     * 提现选项卡ID
     */
    private Integer withdrawalTabId;

    /**
     * 自动打款（0：否，1：是）
     */
    private Integer autoAccount;

    /**
     * 场景（1：系统，2：提现）
     */
    private Integer take;

    /**
     * 打款时间
     */
    private Date payTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;



    /**
     * 支付宝姓名
     */
    private String zfbName;

    /**
     * 支付宝账号
     */
    private String zfbUserName;

    private static final long serialVersionUID = 1L;
}