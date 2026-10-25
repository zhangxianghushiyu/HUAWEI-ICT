package com.ruoyi.platform.controller.platform;

import com.ruoyi.platform.dto.TaKuAdCallbackDTO;
import com.ruoyi.platform.service.*;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "广告信息API")
@Slf4j
@RestController
@RequestMapping("/publicize")
public class PublicizeController {

    @Autowired
    private AdConfigService adConfigService;
    @Autowired
    private TaKuCallbackService taKuCallbackService;

    @ApiOperation(value = "TaKu平台配置")
    @GetMapping("/taKuAdConfig")
    public R<TaKuAdConfigVO> taKuAdConfig() {
        return R.ok(adConfigService.getTaKuAdConfigVO());
    }


    @ApiOperation(value = "TaKu广告回调", hidden = true)
    @GetMapping("/taKuCallback")
    public Integer taKuCallback(HttpServletRequest request, TaKuAdCallbackDTO dto) {
        log.info("==============================================");
        log.info("==================TaKu广告回调===================");
        log.info(dto.toString());
        log.info("==============================================");
        return taKuCallbackService.taKuCallback(request, dto);
    }

    @ApiOperation(value = "TaKu查看广告结果")
    @ApiImplicitParam(name = "transId", value = "交易id(完成观看的唯一交易ID)", required = true)
    @GetMapping("/taKuResultByTransId")
    public R<TaKuAdCallbackVO> taKuResultByTransId(@RequestParam(value = "transId") String transId) {
        return R.ok(taKuCallbackService.getTaKuAdCallbackVO(transId));
    }


    @ApiOperation(value = "验证是否可以拉起广告")
    @GetMapping("/checkIsLookAd")
    public R<Void> checkIsLookAd() {
        taKuCallbackService.checkIsLookAd();
        return R.ok();
    }

    @ApiOperation("验证是否可以拉起赚钱广告")
    @GetMapping("/checkIsLookMakeMoneyAd")
    public R checkIsLookMakeMoneyAd() {
        return R.ok(taKuCallbackService.checkIsLookMakeMoneyAd());
    }


    @ApiOperation(value = "查看今天看广告次数")
    @GetMapping("/lookAdCount")
    public R<Integer> lookAdCount() {
        Integer integer = taKuCallbackService.lookAdCount();
        return R.ok(integer);
    }
}
