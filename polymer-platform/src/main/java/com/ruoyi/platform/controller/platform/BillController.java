package com.ruoyi.platform.controller.platform;


import com.alipay.api.AlipayApiException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.service.BillCashService;
import com.ruoyi.platform.service.BillGoldService;
import com.ruoyi.platform.service.UserService;
import com.ruoyi.platform.service.WithdrawalUserService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.BillCashListVO;
import com.ruoyi.platform.vo.BillGoldListVO;
import com.ruoyi.platform.vo.WithdrawalUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = "账单信息API")
@Slf4j
@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillGoldService billGoldService;
    @Autowired
    private UserService userService;
    @Autowired
    private BillCashService billCashService;
    @Autowired
    private WithdrawalUserService withdrawalUserService;
    @Autowired
    private HttpServletRequest request;


    @ApiOperation(value = "兑换现金")
    @ApiImplicitParam(name = "goldCoin", value = "金币", required = true)
    @RepeatSubmit(interval = 2000)
    @GetMapping("/goldCoinToCash")
    public R goldCoinToCash(@RequestParam(value = "goldCoin") Integer goldCoin) {
        userService.goldCoinToCash(goldCoin);
        return R.ok();
    }

    @ApiOperation(value = "金币明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/billGold")
    public R<PageInfo<BillGoldListVO>> goldCoinInfo(@RequestParam(value = "pageNum") Integer pageNum,
                                                    @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BillGoldListVO> myBillGoldListVO = billGoldService.getMyBillGoldListVO();
        PageInfo<BillGoldListVO> billGoldListVOPageInfo = new PageInfo<>(myBillGoldListVO);
        return R.ok(billGoldListVOPageInfo);
    }


    @ApiOperation(value = "现金明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/billCash")
    public R<PageInfo<BillCashListVO>> billCash(@RequestParam(value = "pageNum") Integer pageNum,
                                                @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BillCashListVO> myBillCashListVO = billCashService.getMyBillCashListVO();
        PageInfo<BillCashListVO> billCashListVOPageInfo = new PageInfo<>(myBillCashListVO);
        return R.ok(billCashListVOPageInfo);
    }


    @ApiOperation(value = "申请提现")
    @ApiImplicitParam(name = "withdrawalTabId", value = "选项卡ID", required = true)
    @GetMapping("/applyWithdrawal")
    @RepeatSubmit(interval = 2000)
    public R applyWithdrawal(@RequestParam(value = "withdrawalTabId") Integer withdrawalTabId,
                             @RequestParam(value = "price", required = false) BigDecimal price,
                             @RequestParam(value = "withdrawalType", required = false) Integer withdrawalType) throws Exception {
        return billCashService.applyWithdrawal(withdrawalTabId, price, withdrawalType);
    }


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
