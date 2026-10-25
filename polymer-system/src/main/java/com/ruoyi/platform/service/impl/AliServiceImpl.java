package com.ruoyi.platform.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import com.tencent.cloud.Credentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import com.ruoyi.common.enums.ParamsConfigFollowTypeEnum;
import com.ruoyi.common.enums.ParamsConfigTypeEnum;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.service.AliService;
import com.ruoyi.platform.service.IParamsConfigService;
import com.ruoyi.platform.vo.AliAccessTokenVO;
import com.ruoyi.platform.vo.AliStsTokenVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class AliServiceImpl implements AliService {

    @Value("${ali.payServerUrl}")
    private String payServerUrl;
    @Autowired
    private IParamsConfigService paramsConfigService;


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
            throw new AlipayApiException("获取access_token失败！");
        }
        // 获取信息
        alipayClient = new DefaultAlipayClient(certAlipayRequest);
        AlipayUserInfoShareRequest getUserInfoRequest = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse getUserInfoResponse = alipayClient.certificateExecute(getUserInfoRequest, getTokenResponse.getAccessToken());
        if (!getUserInfoResponse.isSuccess()) {
            log.error("获取用户支付宝信息失败！{}", JSON.toJSONString(getUserInfoResponse));
            throw new AlipayApiException("获取用户支付宝信息失败！");
        }
        accessTokenVO.setOpenId(getUserInfoResponse.getOpenId());
        accessTokenVO.setNickName(getUserInfoResponse.getNickName());
        accessTokenVO.setHeadimgurl(getUserInfoResponse.getAvatar());
        accessTokenVO.setAccessToken(getTokenResponse.getAccessToken());
        return accessTokenVO;
    }


    @Override
    public String sliceUploadFile(String objectName, MultipartFile file) throws IOException {
        String suffix = objectName.substring(objectName.lastIndexOf("."));
        // 先写入临时文件，再通过 FileInputStream + 显式 Content-Length 上传
        // 确保 COS SDK 不会因为"不知道流长度"而使用 chunked encoding（签名不包含 body）
        File tempFile = File.createTempFile("cos-upload-", suffix);
        try {
            file.transferTo(tempFile);
            long tempFileLen = tempFile.length();
            if (tempFileLen != file.getSize()) {
                throw new IOException(
                        String.format("临时文件大小不一致: 期望=%d, 实际=%d", file.getSize(), tempFileLen));
            }
            return putObjectToCos(tempFile, suffix, tempFileLen);
        } finally {
            if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
        }
    }

    @Override
    public String sliceUploadFile(String objectName, File file) throws IOException {
        String suffix = objectName.substring(objectName.lastIndexOf("."));
        return putObjectToCos(file, suffix, file.length());
    }

    /**
     * 将本地文件上传到 COS，返回可访问的 url
     */
    private String putObjectToCos(File file, String suffix, long length) throws IOException {
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_2.getType());
        if (paramsList.isEmpty()) {
            throw new RuntimeException("获取参数信息失败");
        }
        String secretId = paramsConfigService
                .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_6.getType())
                .getValue();
        String secretKey = paramsConfigService
                .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_7.getType())
                .getValue();
        String bucketName = paramsConfigService
                .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_8.getType())
                .getValue();
        String region = paramsConfigService
                .getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_9.getType())
                .getValue();

        String key = DateUtil.today() + "/" + IdUtil.fastSimpleUUID() + suffix;

        double fileSizeMB = length / (1024.0 * 1024.0);
        log.info("COS上传开始: key={}, size={}MB, origSize={}, bucket={}, region={}",
                key, String.format("%.2f", fileSizeMB), length, bucketName, region);

        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 只设超时，不设 endpointSuffix，避免域名拼接问题
        clientConfig.setSocketTimeout(30 * 60 * 1000);
        clientConfig.setConnectionTimeout(60 * 1000);
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(length);
            try (InputStream fis = new java.io.FileInputStream(file)) {
                PutObjectRequest req = new PutObjectRequest(bucketName, key, fis, metadata);
                cosClient.putObject(req);
            }
            log.info("COS putObject 完成: key={}", key);
        } catch (Exception e) {
            log.error("COS上传失败: key={}, exception={}", key, e.toString(), e);
            throw new IOException("COS上传失败: " + e.getMessage(), e);
        } finally {
            cosClient.shutdown();
        }

        String url = "https://" + bucketName + ".cos." + region + ".myqcloud.com/" + key;
        log.info("COS上传成功: {}", url);
        return url;
    }


    @Override
    public void entPay(String openId, BigDecimal amount) throws AlipayApiException {
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
    public CertAlipayRequest getCertAlipayRequest() {
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_4.getType());
//构造client
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        //设置网关地址
        certAlipayRequest.setServerUrl(payServerUrl);
        //设置应用Id
        certAlipayRequest.setAppId(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_15.getType()).getValue()
        );
        //设置应用私钥
        certAlipayRequest.setPrivateKey(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_16.getType()).getValue()
        );
        //设置请求格式，固定值json
        certAlipayRequest.setFormat("JSON");
        //设置字符集
        certAlipayRequest.setCharset("UTF-8");
        //设置签名类型(商户生成签名字符串所使用的签名算法类型，目前支持 RSA2 和 RSA，推荐使用 RSA2)
        certAlipayRequest.setSignType("RSA2");
        //设置应用公钥证书路径
        certAlipayRequest.setCertPath(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_17.getType()).getValue()
        );
        //设置支付宝公钥证书路径
        certAlipayRequest.setAlipayPublicCertPath(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_18.getType()).getValue()
        );
        //设置支付宝根证书路径
        certAlipayRequest.setRootCertPath(
                paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_19.getType()).getValue()
        );
        return certAlipayRequest;
    }


    @Override
    public AliStsTokenVO getAliStsTokenVO() throws Exception {
        // 使用腾讯云COS STS临时密钥，替代原阿里云AssumeRole
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_2.getType());
        String secretId = paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_6.getType()).getValue();
        String secretKey = paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_7.getType()).getValue();
        String bucket = paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_8.getType()).getValue();
        String region = paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_9.getType()).getValue();

        TreeMap<String, Object> config = new TreeMap<>();
        config.put("secretId", secretId);
        config.put("secretKey", secretKey);
        config.put("durationSeconds", 1800);
        config.put("bucket", bucket);
        config.put("region", region);
        config.put("allowPrefixes", new String[]{"*"});
        config.put("allowActions", new String[]{
                "name/cos:PutObject",
                "name/cos:PostObject",
                "name/cos:InitiateMultipartUpload",
                "name/cos:ListMultipartUploads",
                "name/cos:ListParts",
                "name/cos:UploadPart",
                "name/cos:CompleteMultipartUpload",
                "name/cos:AbortMultipartUpload"
        });

        Response response = CosStsClient.getCredential(config);
        Credentials credentials = response.credentials;

        AliStsTokenVO vo = new AliStsTokenVO();
        vo.setAccessKeyId(credentials.tmpSecretId);
        vo.setAccessKeySecret(credentials.tmpSecretKey);
        vo.setSecurityToken(credentials.sessionToken);
        vo.setBucket(bucket);
        // 构造完整的COS域名: bucket.cos.region.myqcloud.com
        String cosEndpoint = "cos." + region + ".myqcloud.com";
        vo.setEndpoint(cosEndpoint);
        // 同时返回地域代码，供前端SDK使用
        vo.setRegion(region);
        String today = DateUtil.today();
        vo.setToday(today);
        String uuid = IdUtil.fastSimpleUUID();
        vo.setFileName(uuid);
        log.info("CosStsTokenVO:" + vo);
        return vo;
    }
}
