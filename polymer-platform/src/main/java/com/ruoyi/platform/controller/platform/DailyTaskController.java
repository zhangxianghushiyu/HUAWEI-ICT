package com.ruoyi.platform.controller.platform;


import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.service.DailyService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.DailyTaskListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "每日任务API")
@RestController
@Slf4j
@RequestMapping("/platform/dailyTask")
public class DailyTaskController {

    @Autowired
    private DailyService dailyService;
    @Autowired
    private HttpServletRequest request;

    @ApiOperation(value = "每日任务")
    @GetMapping("/daily")
    public R<DailyTaskListVO> daily() {
        return R.ok(dailyService.daily());
    }

    @ApiOperation(value = "每日签到领金币")
    @GetMapping("/dailySignToGoldCoins")
    @RepeatSubmit
    public R<Void> dailySignToGoldCoins() {
        dailyService.dailySignToGoldCoins();
        return R.ok();
    }


        @ApiIgnore
    @ApiOperation(value = "饭点看剧领补贴")
    @GetMapping("/foodPointLookDramaToGoldCoins")
    @RepeatSubmit
    public R<Void> foodPointLookDramaToGoldCoins() {
        dailyService.foodPointLookDramaToGoldCoins(JwtUtil.getRequestUserId(request));
        return R.ok();
    }


        @ApiIgnore
    @ApiOperation(value = "看剧领金币")
    @GetMapping("/lookDramaToCoin")
    @RepeatSubmit
    public R<Void> lookDramaToCoin(@RequestParam(value = "second") Integer second) {
        dailyService.lookDramaToCoin(JwtUtil.getRequestUserId(request), second);
        return R.ok();
    }



    @ApiOperation(value = "看广告领金币")
    @GetMapping("/adRewardToCoin")
    @RepeatSubmit(interval = 2000)
    public R<Void> adRewardToCoin() {
        dailyService.adRewardToCoin(JwtUtil.getRequestUserId(request));
        return R.ok();
    }


    @ApiOperation(value = "分享领金币")
    @GetMapping("/shareToCoin")
    @RepeatSubmit(interval = 2000)
    public R<Void> shareToCoin() {
        dailyService.shareToCoin(JwtUtil.getRequestUserId(request));
        return R.ok();
    }


    @ApiIgnore
    @ApiOperation(value = "看指定剧领金币")
    @GetMapping("/appointDramaCoin")
    @RepeatSubmit
    public R<Void> appointDramaCoin(@RequestParam(value = "dramaId")Integer dramaId,
                                    @RequestParam(value = "second") Integer second) {
        dailyService.appointDramaCoin(JwtUtil.getRequestUserId(request),dramaId,second);
        return R.ok();
    }

}
