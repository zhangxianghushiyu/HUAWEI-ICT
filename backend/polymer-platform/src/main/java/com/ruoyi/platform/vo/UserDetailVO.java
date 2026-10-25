package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * user
 * @author 
 */
@Data
@ApiModel(value="用户详细返回对象")
public class UserDetailVO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nickName;

    /**
     * 账号
     */
    @ApiModelProperty(value="账号")
    private String userName;

    /**
     * 头像
     */
    @ApiModelProperty(value="头像")
    private String avatar;

    /**
     * 邀请码
     */
    @ApiModelProperty(value="邀请码")
    private String invitationCode;

    /**
     * 父级用户ID
     */
    @ApiModelProperty(value="父级用户ID")
    private Integer parentId;

    /**
     * 用户角色id
     */
    private Integer roleId;

    /**
     * 金币
     */
    @ApiModelProperty(value="金币")
    private BigDecimal goldCoin;
    /**
     * 现金
     */
    @ApiModelProperty(value="现金")
    private BigDecimal cash;
    /**
     * 0：不是VIP，1：是VIP
     */
    @ApiModelProperty(value="0：不是VIP，1：是VIP")
    private Integer isVip;

    /**
     * 支付宝姓名
     */
    @ApiModelProperty(value="支付宝姓名")
    private String zfbName;

    /**
     * 支付宝账号
     */
    @ApiModelProperty(value="支付宝账号")
    private String zfbUserName;

    /**
     * 总下级人数
     */
    @ApiModelProperty(value="总下级人数")
    private Integer sumJuniorCount;

    /**
     * 今日收益
     */
    @ApiModelProperty(value="今日收益")
    private BigDecimal todayGetMoney;

    /**
     * 今日下级人数
     */
    @ApiModelProperty(value="今日下级人数")
    private Integer todayJuniorCount;

    /**
     * 昨日下级人数
     */
    @ApiModelProperty(value="昨日下级人数")
    private Integer yesterdayJuniorCount;

    /**
     * 短剧解锁需要的金额
     */
    @ApiModelProperty(value="短剧解锁需要的金额")
    private BigDecimal dramaUnlockMoney;

    private String token;

    @ApiModelProperty(value="信息流页数（小说）")
    private Integer informationFlowPageNum;
    @ApiModelProperty(value="激励分钟（小说）")
    private Integer motivateMinute;

    private String wxOpenId;
    private String wxAvatar;
    private String wxNickName;
    private String wxUnionId;

    private String qrUrl;
    private String qrCode;

    private static final long serialVersionUID = 1L;
}
