package com.ruoyi.platform.controller.platform;


import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.service.NewPeopleService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.NewPeopleTaskListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "新人任务API")
@RestController
@Slf4j
@RequestMapping("/platform/newTask")
public class NewPeopleTaskController {

    @Autowired
    private NewPeopleService newPeopleService;
    @Autowired
    private HttpServletRequest request;

    @ApiOperation(value = "新人任务")
    @GetMapping("/newPeople")
    public R<NewPeopleTaskListVO> newPeople() {
        return R.ok(newPeopleService.newPeopleTask());
    }


    @ApiOperation(value = "见面领现金")
    @GetMapping("/meetToReceiveCash")
    @RepeatSubmit
    public R<Void> meetToReceiveCash() {
        newPeopleService.meetToReceiveCash();
        return R.ok();
    }

    @ApiOperation(value = "新人签到领金币")
    @GetMapping("/newcomersSignToGoldCoins")
    @RepeatSubmit
    public R<Void> newcomersSignToGoldCoins() {
        newPeopleService.newcomersSignToGoldCoins();
        return R.ok();
    }


    @ApiIgnore
    @ApiOperation(value = "提现一元")
    @GetMapping("/withdrawOneYuan")
    @RepeatSubmit
    public R<Void> withdrawOneYuan() {
        newPeopleService.withdrawOneYuan(JwtUtil.getRequestUserId(request));
        return R.ok();
    }


    @ApiIgnore
    @ApiOperation(value = "新人看剧领现金")
    @GetMapping("/newPeopleLookToDramasCash")
    @RepeatSubmit
    public R<Void> newPeopleLookToDramasCash() {
        newPeopleService.newPeopleLookToDramasCash(JwtUtil.getRequestUserId(request));
        return R.ok();
    }

}
