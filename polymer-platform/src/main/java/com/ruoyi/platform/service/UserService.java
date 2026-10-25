package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.dto.*;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.MyInvitationVO;
import com.ruoyi.platform.vo.UserDetailVO;
import com.ruoyi.platform.vo.UserVO;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {


    /**
     * 用户登录
     */
    UserVO userLogin(UserLoginDTO dto);

    /**
     * 账号用户登录
     */
    UserVO accountLogin(AccountLoginDTO dto);

    /**
     * 账号用户注册
     */
    UserVO registerAccount(RegisterAccountDTO dto);

    /**
     * 微信登录
     */
    UserVO wxLogin(WxLoginDTO dto);

    /**
     * 修改密码
     */
    void modifyPassword(ModifyPasswordDTO dto);

    /**
     * 修改支付宝
     */
    void modifyZfb(ModifyZfbDTO dto);

    /**
     * 获取用户信息
     */
    UserDetailVO getUserDetail(Integer requestUserId);

    /**
     * 修改用户信息
     */
    void modify(UserModifyDTO dto);


    /**
     * 检查用户状态是否正常
     *
     * @param user
     */
    void checkUser(User user);


    /**
     * 填写邀请码
     */
    R setInvitationCode(String invitationCode);


    /**
     * 我的邀请记录
     */
    List<MyInvitationVO> myInvitation();


    /**
     * 金币转现金
     */
    void goldCoinToCash(Integer goldCoin);
}
