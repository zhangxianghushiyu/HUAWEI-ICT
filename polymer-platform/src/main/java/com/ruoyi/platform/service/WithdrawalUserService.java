package com.ruoyi.platform.service;

import com.alipay.api.AlipayApiException;
import com.ruoyi.platform.domain.WithdrawalUser;
import com.ruoyi.platform.vo.WithdrawalUserVO;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.List;

public interface WithdrawalUserService {
    /**
     * 查看提现绑定用户信息
     */
    WithdrawalUserVO getWithdrawalUserByTypeAndUserId(
            Integer userId);

    /**
     * 绑定提现账号（微信）
     */
    void bindWxWithdrawalUser(String code) throws WxErrorException;

    /**
     * 绑定提现账号（支付宝）
     */
    void bindAliWithdrawalUser(String code) throws AlipayApiException;
}
