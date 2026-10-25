package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * user
 * @author 
 */
@Data
@ApiModel(value="用户返回对象")
public class UserVO implements Serializable {
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
     * 是不是注册（0：登录，1：注册）
     */
    @ApiModelProperty(value="是不是注册（0：登录，1：注册）")
    private Integer isRegister;

    private String token;

    private static final long serialVersionUID = 1L;
}
