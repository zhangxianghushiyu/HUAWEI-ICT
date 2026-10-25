package com.ruoyi.platform.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.enums.*;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.AliService;
import com.ruoyi.platform.service.ParamsConfigService;
import com.ruoyi.platform.utils.OrderInfoUtil2_0;
import com.ruoyi.platform.vo.AliAccessTokenVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class AliServiceImpl implements AliService {

    @Value("${ali.payServerUrl}")
    private String payServerUrl;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private UserVipDao userVipDao;
    @Autowired
    private ParamsConfigService paramsConfigService;


    @Override
    public AliAccessTokenVO aliCodeToAliAccessTokenVO(String code) throws AlipayApiException {
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_4.getCode());
        AliAccessTokenVO accessTokenVO = new AliAccessTokenVO();
        //构造client
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        //设置网关地址
        certAlipayRequest.setServerUrl(payServerUrl);
        //设置应用Id
        certAlipayRequest.setAppId(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_15.getCode()).getValue()
        );
        //设置应用私钥
        certAlipayRequest.setPrivateKey(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_16.getCode()).getValue()
        );
        //设置请求格式，固定值json
        certAlipayRequest.setFormat("JSON");
        //设置字符集
        certAlipayRequest.setCharset("UTF-8");
        //设置签名类型(商户生成签名字符串所使用的签名算法类型，目前支持 RSA2 和 RSA，推荐使用 RSA2)
        certAlipayRequest.setSignType("RSA2");
        //设置应用公钥证书路径
        certAlipayRequest.setCertPath(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_17.getCode()).getValue()
        );
        //设置支付宝公钥证书路径
        certAlipayRequest.setAlipayPublicCertPath(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_18.getCode()).getValue()
        );
        //设置支付宝根证书路径
        certAlipayRequest.setRootCertPath(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_19.getCode()).getValue()
        );
        log.info("CertAlipayRequest:{}", JSON.toJSONString(certAlipayRequest));
        AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        AlipaySystemOauthTokenRequest getTokenRequest = new AlipaySystemOauthTokenRequest();
        getTokenRequest.setCode(code);
        getTokenRequest.setGrantType("authorization_code");
        AlipaySystemOauthTokenResponse getTokenResponse = alipayClient.certificateExecute(getTokenRequest);
        if (!getTokenResponse.isSuccess()) {
            log.info("获取access_token失败！{}", JSON.toJSONString(getTokenResponse));
            throw new ApiException("获取access_token失败！");
        }
        // 获取信息
        alipayClient = new DefaultAlipayClient(certAlipayRequest);
        AlipayUserInfoShareRequest getUserInfoRequest = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse getUserInfoResponse = alipayClient.certificateExecute(getUserInfoRequest, getTokenResponse.getAccessToken());
        if (!getUserInfoResponse.isSuccess()) {
            log.error("获取用户支付宝信息失败！{}", JSON.toJSONString(getUserInfoResponse));
            throw new ApiException("获取用户支付宝信息失败！");
        }
        accessTokenVO.setOpenId(getUserInfoResponse.getOpenId());
        accessTokenVO.setNickName(getUserInfoResponse.getNickName());
        accessTokenVO.setHeadimgurl(getUserInfoResponse.getAvatar());
        accessTokenVO.setAccessToken(getTokenResponse.getAccessToken());
        return accessTokenVO;
    }

    @Override
    public String sendCode(String phone, String code) throws Exception {
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_1.getCode());
        if (paramsList.isEmpty()) {
            throw new ApiException(ResultCode.NO_GET_PARAMS_CONFIG_INFO);
        }
        Config config = new Config();
        config.setAccessKeyId(paramsConfigService
                .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_1.getCode())
                .getValue());
        config.setAccessKeySecret(paramsConfigService
                .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_2.getCode())
                .getValue());
        // 配置 Endpoint
        config.endpoint = paramsConfigService
                .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_3.getCode())
                .getValue();

        Client client = new Client(config);

        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(paramsConfigService
                        .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_4.getCode())
                        .getValue())
                .setTemplateCode(paramsConfigService
                        .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_5.getCode())
                        .getValue())
                .setTemplateParam("{\"code\":\"" + code + "\"}");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
            log.info(sendSmsResponse.body.toString());
            return ResultMsgEnum.SUCCESS.getMessage();
        } catch (TeaException error) {
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            log.info(error.getMessage());
            // 诊断地址
            log.info(error.getData().get("Recommend").toString());
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            log.info(error.getMessage());
            // 诊断地址
            log.info(error.getData().get("Recommend").toString());
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return ResultMsgEnum.ERROR.getMessage();
    }


    @Override
    public AlipayTradeAppPayResponse createOrder(String orderNo) {
        AlipayTradeAppPayResponse response = null;
        try {
            Order order = orderDao.selectOrderByOrderNo(orderNo);
            if (!order.getPayStatus().equals(PayStatusEnum.STATUS_1.getCode())) {
                throw new ApiException("该订单无法进行支付");
            }
            String body = "购买：";
            if (order.getOrderType().equals(OrderTypeEnum.TYPE_1.getCode())) {
                body = body + OrderTypeEnum.TYPE_1.getMessage();
            }
            //构造client
            AlipayClient alipayClient = new DefaultAlipayClient(getCertAlipayRequest());
            //构造API请求
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            //发送请求
            ParamsConfig paramsConfig = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_8.getCode()).get(0);
            request.setNotifyUrl(paramsConfig.getValue() + "/platform/ali/payNotify");
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            bizContent.put("subject", body);
            bizContent.put("total_amount", order.getPayMoney());
            bizContent.put("product_code", "QUICK_MSECURITY_PAY");
            request.setBizContent(bizContent.toString());
            response = alipayClient.sdkExecute(request);
            log.info(JSON.toJSONString(response));
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("支付宝统一下单异常：{},{}", e.getErrCode(), e.getErrMsg());
        }
        return response;
    }


    @Override
    public String sliceUploadFile(String objectName, MultipartFile file) throws IOException {
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_2.getCode());
        if (paramsList.isEmpty()) {
            throw new ApiException(ResultCode.NO_GET_PARAMS_CONFIG_INFO);
        }
        String today = DateUtil.today();
        String secretId = paramsConfigService
                .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_6.getCode())
                .getValue();
        String secretKey = paramsConfigService
                .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_7.getCode())
                .getValue();
        String bucketName = paramsConfigService
                .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_8.getCode())
                .getValue();
        String region = paramsConfigService
                .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_9.getCode())
                .getValue();

        String path = today + "/";
        String suffix = objectName.substring(objectName.lastIndexOf("."));
        String fileName = IdUtil.fastSimpleUUID() + suffix;
        String key = path + fileName;

        double fileSizeMB = file.getSize() / (1024.0 * 1024.0);
        log.info("COS上传开始: key={}, size={}MB, origSize={}, bucket={}, region={}",
                key, String.format("%.2f", fileSizeMB), file.getSize(), bucketName, region);

        // 先写入临时文件，然后通过 FileInputStream + 显式 Content-Length 上传
        // 确保 COS SDK 不会因为"不知道流长度"而使用 chunked encoding（签名不包含 body）
        File tempFile = File.createTempFile("cos-upload-", suffix);
        try {
            file.transferTo(tempFile);
            long tempFileLen = tempFile.length();
            if (tempFileLen != file.getSize()) {
                throw new IOException(
                    String.format("临时文件大小不一致: 期望=%d, 实际=%d", file.getSize(), tempFileLen));
            }
            log.info("临时文件已写入: {}, size={}", tempFile.getAbsolutePath(), tempFileLen);

            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            // 只设超时，不设 endpointSuffix，避免域名拼接问题
            clientConfig.setSocketTimeout(30 * 60 * 1000);
            clientConfig.setConnectionTimeout(60 * 1000);
            COSClient cosClient = new COSClient(cred, clientConfig);
            try {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(tempFileLen);
                try (InputStream fis = new java.io.FileInputStream(tempFile)) {
                    PutObjectRequest req = new PutObjectRequest(bucketName, key, fis, metadata);
                    cosClient.putObject(req);
                }
                log.info("COS putObject 完成: key={}", key);
            } catch (Exception e) {
                log.error("COS上传失败: key={}, exception={}", e.toString(), e);
                throw new IOException("COS上传失败: " + e.getMessage(), e);
            } finally {
                cosClient.shutdown();
            }
        } finally {
            if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
        }

        String url = "https://" + bucketName + ".cos." + region + ".myqcloud.com/" + key;
        log.info("COS上传成功: {}", url);
        return url;
    }


    @Override
    public CertAlipayRequest getCertAlipayRequest() {
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_4.getCode());
//构造client
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        //设置网关地址
        certAlipayRequest.setServerUrl(payServerUrl);
        //设置应用Id
        certAlipayRequest.setAppId(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_15.getCode()).getValue()
        );
        //设置应用私钥
        certAlipayRequest.setPrivateKey(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_16.getCode()).getValue()
        );
        //设置请求格式，固定值json
        certAlipayRequest.setFormat("JSON");
        //设置字符集
        certAlipayRequest.setCharset("UTF-8");
        //设置签名类型
        certAlipayRequest.setSignType("RSA2");
        //设置应用公钥证书路径
        certAlipayRequest.setCertPath(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_17.getCode()).getValue()
        );
        //设置支付宝公钥证书路径
        certAlipayRequest.setAlipayPublicCertPath(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_18.getCode()).getValue()
        );
        //设置支付宝根证书路径
        certAlipayRequest.setRootCertPath(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_19.getCode()).getValue()
        );
        return certAlipayRequest;
    }

    @Override
    public String aliPayNotifyUrl(HttpServletRequest request) {
        // 验证签名
        String result = "fail";
        // 获取交易状态
        String tradeStatus = request.getParameter("trade_status");
        // 判断交易状态是否已经成功
        if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus)) {
            log.info("params is {}", JSON.toJSONString(request.getParameterMap()));
            // 订单号
            String orderNo = request.getParameter("out_trade_no");
            // 支付宝订单号
            String tradeNo = request.getParameter("trade_no");
//                String totalAmount = request.getParameter("total_amount");
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
            result = "success";
        }
        return result;
    }


    @Override
    public void entPay(Integer userId, String openId, BigDecimal amount) throws AlipayApiException {
        CertAlipayRequest certAlipayRequest = getCertAlipayRequest();
        // 构造client
        AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        model.setOutBizNo(System.currentTimeMillis() + RandomUtil.randomNumbers(4));
        model.setRemark("提现");
        model.setBizScene("DIRECT_TRANSFER");
        Participant payeeInfo = new Participant();
        payeeInfo.setIdentity(openId);
        //收款方标识类型(
        // ALIPAY_USER_ID：支付宝会员的用户ID，可通过 获取会员信息能力获取。
        // ALIPAY_LOGON_ID：支付宝登录号，支持邮箱和手机号格式)
        payeeInfo.setIdentityType("ALIPAY_OPEN_ID");
        model.setPayeeInfo(payeeInfo);
        model.setTransAmount(amount.toPlainString());
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");
        model.setOrderTitle("提现");
        request.setBizModel(model);
        AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
        if (response.isSuccess()) {
            log.info("提现成功");
        } else {
            throw new AlipayApiException(response.getSubMsg());
        }
    }


    @Override
    public AliAccessTokenVO test(String code) throws AlipayApiException {
        return aliCodeToAliAccessTokenVO(code);
    }

    @Override
    public String buildAuthInfo() {
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_4.getCode());
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_28.getCode()).getValue(),
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_15.getCode()).getValue(),
                String.valueOf(System.currentTimeMillis() +RandomUtil.randomInt(0, 20000)),
                true);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String privateKey =
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_16.getCode()).getValue();
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, true);
        return info + "&" + sign;
    }
}
