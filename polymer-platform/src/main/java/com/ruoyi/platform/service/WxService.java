package com.ruoyi.platform.service;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.domain.wx.TransferBillsResult;
import com.ruoyi.platform.dto.WxCreateOrderDTO;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.WxAccessTokenVO;
import me.chanjar.weixin.common.error.WxErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface WxService {


    /**
     * 通过code获取微信用户信息
     */
    WxAccessTokenVO wxCodeToWxAccessTokenVO(String code);


    /**
     * 微信下单
     *
     * @return
     * @throws WxPayException
     */
    R createOrder(WxCreateOrderDTO dto) throws WxPayException, WxErrorException;


    /**
     * 支付回调
     *
     * @param request
     * @param response
     * @return
     */
    String payNotify(HttpServletRequest request, HttpServletResponse response);


    /**
     * 商家转账到零钱
     *
     * @return
     */
    TransferBillsResult transferBatches(String activityName, String rewardDescription,
                                        Integer amount,String orderNo,
                                        String remark, String openId) throws WxPayException;




    /**
     * 微信商家转账到零钱回调
     *
     * @param request
     * @param response
     * @return
     */
    String transferBatchesNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, GeneralSecurityException;



    WxAccessTokenVO test(String code) throws WxErrorException;


    /**
     * 配置支付相关信息
     */
    WxPayConfig configWxPayConfig(List<ParamsConfig> paramsList);
}
