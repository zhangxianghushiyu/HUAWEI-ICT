package com.ruoyi.platform.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.ruoyi.platform.vo.AliAccessTokenVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

public interface AliService {


    /**
     * 通过code获取支付宝用户信息
     */
    AliAccessTokenVO aliCodeToAliAccessTokenVO(String code) throws AlipayApiException;

    /**
     * 关于短信发送的频率限制，同一手机号在一天内接收短信的次数受阿里云平台发送频率限制和运营商侧发送频率限制的双重影响。通常情况下，限制次数确实可能为5次，但具体规定您可以在此查看详细说明。
     * <p>
     * 运营商侧的发送频率限制与阿里云平台的限制基本持平，这意味着如果平台限制为5次，运营商侧也大致相同。
     * <p>
     * 若您的业务需求特殊，需要对某个特定号码提高发送频率，建议在阿里云短信服务的控制台中对该号码进行加白处理。号码加白后，将不再受到阿里云侧的流控限制，从而可以满足更频繁的短信发送需求。
     */
    String sendCode(String phone, String code) throws Exception;

    /**
     * 支付宝回调
     */
    String aliPayNotifyUrl(HttpServletRequest request);

    /**
     * 分片上传文件至oss
     *
     * @return 结果
     */
    String sliceUploadFile(String objectName, MultipartFile file) throws IOException;


    /**
     * 封装支付宝携带证书请求参数
     *
     * @return
     */
    CertAlipayRequest getCertAlipayRequest();


    /**
     * 支付宝下单
     *
     * @return
     */
    AlipayTradeAppPayResponse createOrder(String orderNo);


    /**
     * 支付宝提现
     */
    void entPay(Integer userId, String openId, BigDecimal amount) throws AlipayApiException, ParseException;


    /**
     * 测试code
     * @param code
     * @return
     * @throws AlipayApiException
     */
    AliAccessTokenVO test(String code) throws AlipayApiException;


    /**
     * 支付宝授权
     * @return
     */
    String buildAuthInfo();
}
