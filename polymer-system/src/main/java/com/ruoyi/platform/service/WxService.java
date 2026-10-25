package com.ruoyi.platform.service;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.domain.wx.TransferBillsResult;

import java.util.List;

public interface WxService {

    /**
     * 商家转账到零钱
     *
     * @return
     */
    TransferBillsResult transferBatches(String activityName, String rewardDescription,
                                        Integer amount,String orderNo,
                                        String remark, String openId) throws WxPayException;



    /**
     * 配置支付相关信息
     */
    WxPayConfig configWxPayConfig(List<ParamsConfig> paramsList);
}
