package com.ruoyi.platform.controller.platform;


import com.github.binarywang.wxpay.exception.WxPayException;
import com.ruoyi.platform.service.WxService;
import com.ruoyi.platform.utils.GetTransferBillByOutNo;
import com.ruoyi.platform.vo.WxAccessTokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;


@Api(tags = "微信信息API")
@RestController
@Slf4j
@RequestMapping("/platform/wx")
public class WxController {

    @Autowired
    private WxService wxService;





    @ApiOperation(value = "微信商家转账到零钱回调", hidden = true)
    @RequestMapping("/transferBatchesNotify")
    public String transferBatchesNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, GeneralSecurityException {
        return wxService.transferBatchesNotify(request, response);
    }



    @ApiOperation(value = "测试code")
    @GetMapping("/test")
    public WxAccessTokenVO test(String code) throws WxPayException, WxErrorException {
        GetTransferBillByOutNo client = new GetTransferBillByOutNo(
                "1720302720",                    // 商户号，是由微信支付系统生成并分配给每个商户的唯一标识符，商户号获取方式参考 https://pay.weixin.qq.com/doc/v3/merchant/4013070756
                "29033B2483BF7D938C9C4664C80C83693FA86308",         // 商户API证书序列号，如何获取请参考 https://pay.weixin.qq.com/doc/v3/merchant/4013053053
                "/mnt/wx/pay/1751858291191_091725_apiclient_key.pem",    // 商户API证书私钥文件路径，本地文件路径
                "PUB_KEY_ID_0117203027202025062300391536000001",      // 微信支付公钥ID，如何获取请参考 https://pay.weixin.qq.com/doc/v3/merchant/4013038816
                "/mnt/wx/pay/1752894336737_280057_pub_key.pem"          // 微信支付公钥文件路径，本地文件路径
        );
        client.testQueryInfo(client);

        return wxService.test(code);
    }
}
