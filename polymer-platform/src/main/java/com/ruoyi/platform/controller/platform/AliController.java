package com.ruoyi.platform.controller.platform;


import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.service.AliService;
import com.ruoyi.platform.service.BillCashService;
import com.ruoyi.platform.service.WithdrawalUserService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.AliAccessTokenVO;
import com.ruoyi.platform.vo.WithdrawalUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "支付宝信息API")
@RestController
@Slf4j
@RequestMapping("/platform/ali")
public class AliController {

    @Autowired
    private AliService aliService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private BillCashService billCashService;
    @Autowired
    private WithdrawalUserService withdrawalUserService;

    @ApiOperation(value = "支付宝统一下单")
    @GetMapping("/createOrder")
    @ApiImplicitParam(name = "orderNo", value = "订单号")
    public R<String> createOrder(@RequestParam("orderNo") String orderNo) {
        AlipayTradeAppPayResponse response = aliService.createOrder(orderNo);
        if (ObjectUtils.isNotEmpty(response) && response.isSuccess()) {
            return R.ok(response.getBody());
        } else {
            log.error("获取调起支付宝支付参数失败！具体原因：{}", response.getSubMsg());
            R.error(response.getSubMsg());
        }
        return R.error("支付失败");
    }



    @ApiOperation(value = "测试code")
    @GetMapping("/test")
    public AliAccessTokenVO test(String code) throws AlipayApiException {
        return aliService.test(code);
    }

    @ApiOperation(value = "获取授权请求")
    @GetMapping("/buildAuthInfo")
    public R<String> buildAuthInfo(){
        return R.ok(aliService.buildAuthInfo());
    }

//    @ApiOperation(value = "申请提现")
//    @ApiImplicitParam(name = "withdrawalTabId", value = "选项卡ID", required = true)
//    @GetMapping("/applyWithdrawal")
//    @RepeatSubmit(interval = 2000)
//    public R applyWithdrawal(@RequestParam(value = "withdrawalTabId") Integer withdrawalTabId) throws Exception {
//        return billCashService.applyWithdrawal(withdrawalTabId);
//    }


    @ApiOperation(value = "查询是否已经绑定提现账号")
    @GetMapping("/withdrawalUser")
    public R<WithdrawalUserVO> withdrawalInfo() {
        return R.ok(withdrawalUserService.getWithdrawalUserByTypeAndUserId(JwtUtil.getRequestUserId(request)));
    }



    @ApiOperation(value = "绑定微信提现账号")
    @ApiImplicitParam(name = "code", value = "微信code")
    @GetMapping("/bindWxWithdrawalUser")
    public R<Boolean> bindWxWithdrawalUser(@RequestParam(value = "code") String code) throws WxErrorException {
        withdrawalUserService.bindWxWithdrawalUser(code);
        return R.ok();
    }


    @ApiOperation(value = "绑定支付宝提现账号")
    @ApiImplicitParam(name = "code", value = "微信code")
    @GetMapping("/bindAliWithdrawalUser")
    public R<Boolean> bindAliWithdrawalUser(@RequestParam(value = "code") String code) throws AlipayApiException {
        withdrawalUserService.bindAliWithdrawalUser(code);
        return R.ok();
    }
}
