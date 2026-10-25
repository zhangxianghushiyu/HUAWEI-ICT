package com.ruoyi.platform.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alipay.api.AlipayApiException;
import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.domain.WithdrawalUser;
import com.ruoyi.platform.enums.WithdrawalUserEnum;
import com.ruoyi.platform.mapper.UserDao;
import com.ruoyi.platform.mapper.WithdrawalUserDao;
import com.ruoyi.platform.service.AliService;
import com.ruoyi.platform.service.UserService;
import com.ruoyi.platform.service.WithdrawalUserService;
import com.ruoyi.platform.service.WxService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.AliAccessTokenVO;
import com.ruoyi.platform.vo.WithdrawalUserVO;
import com.ruoyi.platform.vo.WxAccessTokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Service
public class WithdrawalUserServiceImpl implements WithdrawalUserService {

    @Autowired
    private WithdrawalUserDao withdrawalUserDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;
    @Autowired
    private WxService wxService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AliService aliService;

    @Override
    public WithdrawalUserVO getWithdrawalUserByTypeAndUserId(Integer userId) {
        WithdrawalUserVO vo = new WithdrawalUserVO();
        WithdrawalUser withdrawalUser1 = withdrawalUserDao.selectWithdrawalUserByTypeAndUserId(1, userId);
        WithdrawalUser withdrawalUser2 = withdrawalUserDao.selectWithdrawalUserByTypeAndUserId(2, userId);
        if (Objects.nonNull(withdrawalUser1)) {
            vo.setWxIsBind(1);
        }
        if (Objects.nonNull(withdrawalUser2)) {
            vo.setAliIsBind(1);
        }
        return vo;
    }


    @Override
    public void bindWxWithdrawalUser(String code) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        User user = userDao.selectByPrimaryKey(requestUserId);
        userService.checkUser(user);
        //获取微信相关信息
        WxAccessTokenVO wxAccessTokenVO = wxService.wxCodeToWxAccessTokenVO(code);
        if (Objects.isNull(wxAccessTokenVO)) {
            throw new ApiException("获取微信信息失败");
        }
        String openId = wxAccessTokenVO.getOpenId();
        //查看之前绑定信息
        WithdrawalUser withdrawalUser = withdrawalUserDao.selectWithdrawalUserByTypeAndUserId(WithdrawalUserEnum.WEI.getCode(),
                requestUserId);
        if (Objects.nonNull(withdrawalUser)) {
            //修改
            withdrawalUser.setOpenId(openId);
            withdrawalUser.setNickName(wxAccessTokenVO.getNickName());
            withdrawalUser.setUnionId(wxAccessTokenVO.getUnionId());
            withdrawalUser.setHeadImgUrl(wxAccessTokenVO.getHeadimgurl());
            withdrawalUserDao.updateByPrimaryKeySelective(withdrawalUser);
        } else {
            //创建
            WithdrawalUser withdrawalUser1 = new WithdrawalUser();
            withdrawalUser1.setUserId(requestUserId);
            withdrawalUser1.setType(WithdrawalUserEnum.WEI.getCode());
            withdrawalUser1.setOpenId(openId);
            withdrawalUser1.setNickName(wxAccessTokenVO.getNickName());
            withdrawalUser1.setUnionId(wxAccessTokenVO.getUnionId());
            withdrawalUser1.setHeadImgUrl(wxAccessTokenVO.getHeadimgurl());
            withdrawalUser1.setCreateTime(new Date());
            withdrawalUserDao.insertSelective(withdrawalUser1);
        }
        user.setWxOpenId(openId);
        user.setWxNickName(wxAccessTokenVO.getNickName());
        user.setWxAvatar(wxAccessTokenVO.getHeadimgurl());
        user.setWxUnionId(wxAccessTokenVO.getUnionId());
        userDao.updateByPrimaryKeySelective(user);
    }


    @Override
    public void bindAliWithdrawalUser(String code) throws AlipayApiException {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        User user = userDao.selectByPrimaryKey(requestUserId);
        userService.checkUser(user);
        //获取支付宝相关信息
        AliAccessTokenVO aliAccessTokenVO = aliService.aliCodeToAliAccessTokenVO(code);
        if (Objects.isNull(aliAccessTokenVO)) {
            throw new ApiException("获取支付宝信息失败");
        }
        String openId = aliAccessTokenVO.getOpenId();
        //查看之前绑定信息
        WithdrawalUser withdrawalUser = withdrawalUserDao.selectWithdrawalUserByTypeAndUserId(WithdrawalUserEnum.ALI.getCode(), requestUserId);
        if (Objects.nonNull(withdrawalUser)) {
            //修改
            withdrawalUser.setOpenId(openId);
            withdrawalUser.setNickName(aliAccessTokenVO.getNickName());
            withdrawalUser.setUnionId(aliAccessTokenVO.getUnionId());
            withdrawalUser.setHeadImgUrl(aliAccessTokenVO.getHeadimgurl());
            withdrawalUserDao.updateByPrimaryKeySelective(withdrawalUser);
        } else {
            //创建
            WithdrawalUser withdrawalUser1 = new WithdrawalUser();
            withdrawalUser1.setUserId(requestUserId);
            withdrawalUser1.setType(WithdrawalUserEnum.ALI.getCode());
            withdrawalUser1.setOpenId(openId);
            withdrawalUser1.setNickName(aliAccessTokenVO.getNickName());
            withdrawalUser1.setUnionId(aliAccessTokenVO.getUnionId());
            withdrawalUser1.setHeadImgUrl(aliAccessTokenVO.getHeadimgurl());
            withdrawalUser1.setCreateTime(new Date());
            withdrawalUserDao.insertSelective(withdrawalUser1);
        }
    }
}
