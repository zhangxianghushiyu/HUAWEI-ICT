package com.ruoyi.platform.service.impl;


import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.v3.util.RsaCryptoUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ruoyi.common.enums.ParamsConfigFollowTypeEnum;
import com.ruoyi.common.enums.ParamsConfigTypeEnum;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.domain.wx.TransferBillsRequest;
import com.ruoyi.platform.domain.wx.TransferBillsResult;
import com.ruoyi.platform.service.IParamsConfigService;
import com.ruoyi.platform.service.WxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WxServiceImpl implements WxService {


    @Lazy
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private IParamsConfigService paramsConfigService;

    private static final Gson GSON = new GsonBuilder().create();

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
