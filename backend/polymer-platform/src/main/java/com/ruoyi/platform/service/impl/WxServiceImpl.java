package com.ruoyi.platform.service.impl;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Response;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.v3.util.AesUtils;
import com.github.binarywang.wxpay.v3.util.RsaCryptoUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.domain.wx.TransferBillsRequest;
import com.ruoyi.platform.domain.wx.TransferBillsResult;
import com.ruoyi.platform.dto.WxCreateOrderDTO;
import com.ruoyi.platform.enums.*;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.ParamsConfigService;
import com.ruoyi.platform.service.WxService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.WxAccessTokenVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenOAuth2ServiceImpl;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class WxServiceImpl implements WxService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserVipDao userVipDao;
    @Autowired
    private BillCashDao billCashDao;
    @Lazy
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private ParamsConfigService paramsConfigService;

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public WxAccessTokenVO wxCodeToWxAccessTokenVO(String code) {
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_3.getCode());
        WxOpenOAuth2ServiceImpl wxOpenOAuth2Service = new WxOpenOAuth2ServiceImpl(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_10.getCode()).getValue(),
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_11.getCode()).getValue(),
                new WxOpenInMemoryConfigStorage());
        try {
            WxOAuth2AccessToken accessToken = wxOpenOAuth2Service.getAccessToken(code);
            WxOAuth2UserInfo userInfo = wxOpenOAuth2Service.getUserInfo(accessToken, "");
            log.info(userInfo.toString());
            WxAccessTokenVO accessTokenVO = new WxAccessTokenVO();
            accessTokenVO.setOpenId(accessToken.getOpenId());
            accessTokenVO.setUnionId(accessToken.getUnionId());
            accessTokenVO.setNickName(userInfo.getNickname());
            accessTokenVO.setHeadimgurl(userInfo.getHeadImgUrl());
            return accessTokenVO;
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }


    @Override
    @Transactional
    public R createOrder(WxCreateOrderDTO dto) throws WxPayException {
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_3.getCode());
        wxPayService.setConfig(configWxPayConfig(paramsList));

        Order order = orderDao.selectOrderByOrderNo(dto.getOrderNo());
        if (!order.getPayStatus().equals(PayStatusEnum.STATUS_1.getCode())) {
            throw new ApiException("该订单无法进行支付");
        }
        String body = "购买：";
        if (order.getOrderType().equals(OrderTypeEnum.TYPE_1.getCode())) {
            body = body + OrderTypeEnum.TYPE_1.getMessage();
        }
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setOutTradeNo(dto.getOrderNo());
        request.setTotalFee(BaseWxPayRequest.yuanToFen(order.getPayMoney().toString()));//元转成分
        request.setSpbillCreateIp("127.0.0.1");
        request.setBody(body);
        request.setTradeType(WxPayConstants.TradeType.APP);
        ParamsConfig paramsConfig = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_8.getCode()).get(0);
        request.setNotifyUrl(paramsConfig.getValue() + "/platform/wx/payNotify");
        request.setNonceStr(dto.getOrderNo());
        Object order1 = wxPayService.createOrder(request);
        orderDao.updateByPrimaryKeySelective(order);
        return R.ok(order1);
    }

    @Override
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(xmlResult);
            log.info("WxPayOrderNotifyResult:{}", result);
            String resultCode = result.getResultCode();
            if ("SUCCESS".equals(resultCode)) {
                // 加入自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
                String orderNo = result.getOutTradeNo();
                // 微信返回的订单总金额
                Order order = orderDao.selectOrderByOrderNo(orderNo);
                order.setPayTime(new Date());
                order.setPayStatus(PayStatusEnum.STATUS_2.getCode());
                orderDao.updateByPrimaryKeySelective(order);
                //查看订单详细
                OrderItem orderItem = orderItemDao.selectOrderItemByOrderNo(orderNo);
                orderItem.setPayStatus(PayStatusEnum.STATUS_2.getCode());
                orderItemDao.updateByPrimaryKeySelective(orderItem);
                //查看是不是会员，处理加会员天数的操作
                if (order.getOrderType().equals(OrderTypeEnum.TYPE_1.getCode())) {
                    //加用户会员信息
                    User user = userDao.selectByPrimaryKey(order.getUserId());
                    user.setIsVip(1);
                    userDao.updateByPrimaryKeySelective(user);
                    //查询是不是存了vip数据
                    UserVip userVip = userVipDao.selectUserVipByUserId(user.getId());
                    //常用偏移
                    Date date = new Date();
                    //时间偏移
                    DateTime newDate = DateUtil.offsetDay(date, orderItem.getVipDay());
                    if (Objects.isNull(userVip)) {
                        UserVip userVip1 = new UserVip();
                        userVip1.setUserId(order.getUserId());
                        userVip1.setCreateTime(date);
                        userVip1.setStartTime(date);
                        userVip1.setEndTime(newDate);
                        userVipDao.insertSelective(userVip1);
                    } else {
                        //比较时间大小
                        long time = date.getTime();
                        long time1 = userVip.getEndTime().getTime();
                        if (time >= time1) {
                            //当前时间大于等于之前的结束时间，说明之前的已经过期
                            userVip.setStartTime(date);
                            userVip.setEndTime(newDate);
                        } else {
                            //需要在之前的时间上加
                            DateTime dateTime = DateUtil.offsetDay(userVip.getEndTime(), orderItem.getVipDay());
                            userVip.setStartTime(date);
                            userVip.setEndTime(dateTime);
                        }
                        userVipDao.updateByPrimaryKeySelective(userVip);
                    }
                }
                log.info("支付回调返回成功");
                return "<xml>"
                        + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>"
                        + "</xml> ";
            }
        } catch (Exception e) {
            log.error("微信回调结果异常,异常原因{}", e.getMessage());
        }
        log.info("微信回调失败");
        return null;
    }


    @Override
    public TransferBillsResult transferBatches(String activityName, String rewardDescription,
                                               Integer amount, String orderNo,
                                               String remark, String openId) throws WxPayException {
        List<TransferBillsRequest.TransferSceneReportInfo> transferSceneReportInfos = new ArrayList<>();
        TransferBillsRequest.TransferSceneReportInfo build1 = TransferBillsRequest.TransferSceneReportInfo.newBuilder().
                infoContent(activityName)
                .infoType("活动名称")
                .build();
        TransferBillsRequest.TransferSceneReportInfo build2 = TransferBillsRequest.TransferSceneReportInfo.newBuilder().
                infoContent(rewardDescription)
                .infoType("奖励说明")
                .build();
        transferSceneReportInfos.add(build1);
        transferSceneReportInfos.add(build2);

        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_3.getCode());
        ParamsConfig paramsConfig = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_8.getCode()).get(0);

        TransferBillsRequest transferBillsRequest = TransferBillsRequest.newBuilder()
                .transferSceneReportInfos(transferSceneReportInfos)
                .appid(
                        paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_10.getCode()).getValue()
                )
                .outBillNo(orderNo)
                //1000此处写死，该值是在微信处申请获得
                .transferSceneId("1000")
                .transferAmount(amount)
                .transferRemark(remark)
                .openid(openId)
                .userRecvPerception("现金奖励")
                .notifyUrl(paramsConfig.getValue() + "/platform/wx/transferBatchesNotify")
                .build();
        String url = String.format("%s/v3/fund-app/mch-transfer/transfer-bills", this.wxPayService.getPayBaseUrl());
        if (transferBillsRequest.getUserName() != null && transferBillsRequest.getUserName().length() > 0) {
            X509Certificate validCertificate = this.wxPayService.getConfig().getVerifier().getValidCertificate();
            RsaCryptoUtil.encryptFields(transferBillsRequest, validCertificate);
        }
        String result = this.wxPayService.postV3WithWechatpaySerial(url, GSON.toJson(transferBillsRequest));
        return GSON.fromJson(result, TransferBillsResult.class);
    }

    @Transactional
    @Override
    public String transferBatchesNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, GeneralSecurityException {
        log.info("进入提现回调================================");
        String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
        log.info("xmlResult:" + xmlResult);
        JSONObject jsonObject = JSONObject.parseObject(xmlResult);
        String resource = jsonObject.get("resource").toString();
        JSONObject jsonObjectInfo = JSONObject.parseObject(resource);
        // 处理业务逻辑 ...
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_3.getCode());
        String value = paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_13.getCode()).getValue();
        AesUtils aesUtils = new AesUtils(
                value
                        .replaceAll("\"", "")
                        .getBytes(StandardCharsets.UTF_8)
        );
        //对回调的数据进行解密
        String result = aesUtils.decryptToString(
                jsonObjectInfo.get("associated_data").toString().replaceAll("\"", "")
                        .getBytes(StandardCharsets.UTF_8),
                jsonObjectInfo.get("nonce").toString().replaceAll("\"", "")
                        .getBytes(StandardCharsets.UTF_8),
                jsonObjectInfo.get("ciphertext").toString()
                        .replaceAll("\"", "")
        );

        log.info("微信提现回调解密数据：{}", result);
        JSONObject resultJsonObject = JSONObject.parseObject(result);
        String state = resultJsonObject.get("state").toString();
        String outBillNo = resultJsonObject.get("out_bill_no").toString();
        if ("SUCCESS".equals(state)) {
            //查询信息,修改数据
            BillCash billCash = billCashDao.selectBillCashByOrderNo(outBillNo);
            if (Objects.nonNull(billCash)) {
                if (billCash.getMethod() == 1) {
                    //是微信
                    if (billCash.getStatus().equals(BillCashStatusEnum.STATUS_3.getCode())) {
                        //没有进行打款
                        //此处要进行打款，并且要扣除金额
                        billCash.setStatus(BillCashStatusEnum.STATUS_1.getCode());
                        billCash.setPayTime(new Date());
                        billCashDao.updateByPrimaryKeySelective(billCash);
                        log.info("微信转账回调完成================================");
                    }
                }
            }
        }
        return WxPayNotifyV3Response.success("成功");
    }

    @Override
    public WxAccessTokenVO test(String code) throws WxErrorException {
        WxAccessTokenVO wxAccessTokenVO = wxCodeToWxAccessTokenVO(code);
        if (Objects.isNull(wxAccessTokenVO)) {
            throw new ApiException("获取微信信息失败");
        }
        return wxAccessTokenVO;
    }


    @Override
    public WxPayConfig configWxPayConfig(List<ParamsConfig> paramsList) {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_10.getCode()).getValue()
        ));
        payConfig.setMchId(StringUtils.trimToNull(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_12.getCode()).getValue()
        ));
        payConfig.setMchKey(StringUtils.trimToNull(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_13.getCode()).getValue()
        ));
        payConfig.setKeyPath(StringUtils.trimToNull(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_14.getCode()).getValue()
        ));
        payConfig.setApiV3Key(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_13.getCode()).getValue()
        );
        payConfig.setPrivateKeyPath(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_29.getCode()).getValue()
        );
        payConfig.setPrivateCertPath(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_30.getCode()).getValue()
        );
        String value1 = paramsConfigService.
                getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_31.getCode()).getValue();
        String value2 = paramsConfigService.
                getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_32.getCode()).getValue();
        if (!"1".equals(value1) & !"1".equals(value2)) {
            log.info("使用微信商户秘钥验签");
            payConfig.setPublicKeyId(
                    value1
            );
            payConfig.setPublicKeyPath(
                    value2
            );
        } else {
            log.info("使用微信平台证书验签");
        }
        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);
        return payConfig;
    }
}
