package com.ruoyi.platform.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.ruoyi.platform.vo.AliAccessTokenVO;
import com.ruoyi.platform.vo.AliStsTokenVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

public interface AliService {

    /**
     * 通过code获取支付宝用户信息
     */
    AliAccessTokenVO aliCodeToAliAccessTokenVO(String code) throws AlipayApiException;

    /**
     * 分片上传文件至oss
     *
     * @return 结果
     */
    String sliceUploadFile(String objectName, MultipartFile file) throws IOException;


    /**
     * 上传本地文件至 oss（用于转码后的视频回传）
     *
     * @return 结果
     */
    String sliceUploadFile(String objectName, File file) throws IOException;


    /**
     * 支付宝提现
     */
    void entPay(String openId, BigDecimal amount) throws AlipayApiException, ParseException;


    /**
     * 封装支付宝携带证书请求参数
     *
     * @return
     */
    CertAlipayRequest getCertAlipayRequest();


    /**
     * 获取腾讯云COS STS临时凭证
     */
    AliStsTokenVO getAliStsTokenVO() throws Exception;
}
