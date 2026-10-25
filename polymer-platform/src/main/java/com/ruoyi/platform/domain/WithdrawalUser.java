package com.ruoyi.platform.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户提现绑定信息
 * t_withdrawal_user
 */
@Data
public class WithdrawalUser implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 类型：（1：微信，2：支付宝）
     */
    private Integer type;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 微信openId
     */
    private String openId;
    /**
     * 微信昵称
     */
    private String nickName;
    /**
     * 微信头像
     */
    private String headImgUrl;
    /**
     * 微信unionId
     */
    private String unionId;

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
