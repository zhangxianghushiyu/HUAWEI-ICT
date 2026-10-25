package com.ruoyi.platform.controller.platform;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.dto.*;
import com.ruoyi.platform.service.ApplyDestroyService;
import com.ruoyi.platform.service.UserLoginLogService;
import com.ruoyi.platform.service.UserService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.UserDetailVO;
import com.ruoyi.platform.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息Controller
 *
 * @author ruoyi
 * @date 2023-08-10
 */
@Api(tags = "用户信息API")
@RestController
@Slf4j
@RequestMapping("/platform/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ApplyDestroyService applyDestroyService;
    @Autowired
    private UserLoginLogService userLoginLogService;


    @ApiOperation(value = "手机号登陆")
    @PostMapping("/login")
    public R<UserVO> userLogin(@Valid @RequestBody UserLoginDTO dto) {
        return R.ok(userService.userLogin(dto));
    }

    @ApiOperation(value = "账号登陆")
    @PostMapping("/accountLogin")
    public R<UserVO> accountLogin(@Valid @RequestBody AccountLoginDTO dto) {
        return R.ok(userService.accountLogin(dto));
    }

    @ApiOperation(value = "注册账号")
    @PostMapping("/registerAccount")
    public R<UserVO> registerAccount(@Valid @RequestBody RegisterAccountDTO dto) {
        return R.ok(userService.registerAccount(dto));
    }

    @ApiOperation(value = "微信登陆")
    @PostMapping("/wxLogin")
    public R<UserVO> wxLogin(@Valid @RequestBody WxLoginDTO dto) {
        return R.ok(userService.wxLogin(dto));
    }


    @ApiOperation(value = "修改密码")
    @PostMapping("/modifyPassword")
    public R<Void> modifyPassword(@Valid @RequestBody ModifyPasswordDTO dto) {
        userService.modifyPassword(dto);
        return R.ok();
    }


    @ApiOperation(value = "修改支付宝信息")
    @PostMapping("/modifyZfb")
    public R<Void> modifyZfb(@Valid @RequestBody ModifyZfbDTO dto) {
        userService.modifyZfb(dto);
        return R.ok();
    }


    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getInfo")
    public R<UserDetailVO> getInfo() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        return R.ok(userService.getUserDetail(requestUserId));
    }


    @ApiOperation(value = "修改用户信息")
    @PostMapping("/modify")
    public R modify(@Valid @RequestBody UserModifyDTO dto) {
        userService.modify(dto);
        return R.ok();
    }



    @ApiOperation(value = "申请注销")
    @GetMapping("/applyDestroy")
    @RepeatSubmit
    public R<Void> applyDestroy() {
        applyDestroyService.userApplyDestroy();
        return R.ok();
    }


    @ApiOperation(value = "登录日志")
    @GetMapping("/loginInfo")
    public R<Void> loginInfo() {
        userLoginLogService.addUserLoginLog();
        return R.ok();
    }
}
