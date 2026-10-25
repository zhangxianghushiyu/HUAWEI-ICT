package com.ruoyi.platform.controller.platform;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.dto.ActivitySignupDTO;
import com.ruoyi.platform.service.ActivityService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.ActivityDetailVO;
import com.ruoyi.platform.vo.ActivityListVO;
import com.ruoyi.platform.vo.MySignupListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 活动API
 */
@Api(tags = "活动API")
@RestController
@Slf4j
@RequestMapping("/platform/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @ApiOperation(value = "活动列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/list")
    public R<PageInfo<ActivityListVO>> list(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ActivityListVO> list = activityService.getActivityList();
        PageInfo<ActivityListVO> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }

    @ApiOperation(value = "活动详情")
    @ApiImplicitParam(name = "activityId", value = "活动ID", required = true)
    @GetMapping("/detail")
    public R<ActivityDetailVO> detail(@RequestParam(value = "activityId") Long activityId) {
        ActivityDetailVO detail = activityService.getActivityDetail(activityId);
        return R.ok(detail);
    }

    @ApiOperation(value = "报名活动")
    @PostMapping("/signup")
    @RepeatSubmit(interval = 2000)
    public R<Void> signup(@Valid @RequestBody ActivitySignupDTO dto) {
        activityService.signupActivity(dto);
        return R.ok();
    }

    @ApiOperation(value = "取消报名")
    @ApiImplicitParam(name = "activityId", value = "活动ID", required = true)
    @GetMapping("/cancelSignup")
    @RepeatSubmit(interval = 2000)
    public R<Void> cancelSignup(@RequestParam(value = "activityId") Long activityId) {
        activityService.cancelSignup(activityId);
        return R.ok();
    }

    @ApiOperation(value = "我的报名记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/mySignup")
    public R<PageInfo<MySignupListVO>> mySignup(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MySignupListVO> list = activityService.getMySignupList();
        PageInfo<MySignupListVO> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }
}
