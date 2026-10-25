package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 用户信息
 * t_user
 */
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账号
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;
    /**
     * 邀请码
     */
    private String invitationCode;
    /**
     * 0：不是VIP，1：是VIP
     */
    private Integer isVip;
    /**
     * 父级用户ID
     */
    private Integer parentId;
    /**
     * 邀请时间
     */
    private Date invitationTime;
    /**
     * 0：删除，1：正常
     */
    private Integer delStatus;
    /**
     * 0：停用，1：正常，2：注销
     */
    private Integer status;
    /**
     * 用户角色id
     */
    private Integer roleId;
    /**
     * 0：普通，1：代理
     */
    private Integer commander;

    /**
     * 代理比例
     */
    private BigDecimal commanderRatio;
    /**
     * 有上级比例
     */
    private BigDecimal haveManagerRatio;
    /**
     * 无上级比例
     */
    private BigDecimal noManagerRatio;

    /**
     * 金币
     */
    private BigDecimal goldCoin;
    /**
     * 现金
     */
    private BigDecimal cash;

    /**
     * 注销时间
     */
    private Date destroyTime;

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

    private String wxOpenId;
    private String wxAvatar;
    private String wxNickName;
    private String wxUnionId;

    private Integer grade;
    private Integer barrier;
    /**
     * 下级收益方式（1：ECPM，2：范围值）
     */
    private Integer getType;


    /**
     * 获取金额最小值
     */
    private BigDecimal goldMin;

    /**
     * 获取金额最大值
     */
    private BigDecimal goldMax;
    private Integer juniorGetType;
    private BigDecimal juniorGoldMin;
    private BigDecimal juniorGoldMax;
    private String lastIp;

    private String qrUrl;
    private String qrCode;

    private static final long serialVersionUID = 1L;
}