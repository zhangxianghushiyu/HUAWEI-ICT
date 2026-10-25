package com.ruoyi.platform.controller.platform;


import com.ruoyi.platform.service.UserService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.MyInvitationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "邀请信息API")
@RestController
@Slf4j
@RequestMapping("/platform/invitation")
public class InvitationController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "填写邀请码")
    @ApiImplicitParam(name = "invitationCode", value = "邀请码")
    @GetMapping("/setInvitationCode")
    public R setInvitationCode(@RequestParam(value = "invitationCode") String invitationCode) {
        return userService.setInvitationCode(invitationCode);
    }

    @ApiOperation(value = "我的邀请记录")
    @GetMapping("/myInvitation")
    public R<List<MyInvitationVO>> myInvitation() {
        return R.ok(userService.myInvitation());
    }
}
