package com.ruoyi.web.controller.system;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysUserService;

/**
 * 个人信息 业务处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IUserService userService;

    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile() {
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();
        Long userId = SecurityUtils.getUserId();
        boolean admin = SecurityUtils.isAdmin(userId);
        if (!admin) {
            //查询是不是绑定了账号
            SysUser sysUser = sysUserService.selectUserById(userId);
            if (Objects.nonNull(sysUser.getBindUserId())) {
                //查询绑定账号信息
                User user1 = userService.selectUserById(sysUser.getBindUserId().longValue());
                if (Objects.nonNull(user1) && Objects.nonNull(user1.getCommanderRatio())) {
                    user.setCommanderRatio(user1.getCommanderRatio());
                }
            }
        }
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", sysUserService.selectUserRoleGroup(loginUser.getUsername()));
        ajax.put("postGroup", sysUserService.selectUserPostGroup(loginUser.getUsername()));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = loginUser.getUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(sysUserService.checkEmailUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUserId(sysUser.getUserId());
        user.setPassword(null);
        //处理修改自己的代理收益比例
        Long userId = SecurityUtils.getUserId();
        boolean admin = SecurityUtils.isAdmin(userId);
        if (!admin) {
            //查询是不是绑定了账号
            SysUser sysUser2 = sysUserService.selectUserById(userId);
            if (Objects.nonNull(sysUser2.getBindUserId())) {
                //查询绑定账号信息
                User user1 = userService.selectUserById(sysUser.getBindUserId().longValue());
                if (Objects.nonNull(user1)) {
                    //修改这个用户的代理比例信息
                    BigDecimal commanderRatio = user.getCommanderRatio();
                    double v = commanderRatio.doubleValue();
                    if (v < 0 || v > 100) {
                        return AjaxResult.error("代理比例请输入0-100之间的值");
                    }
                    //查询该用户的所有下级，是不是和代理比例计算之后超过100，如果超过100，那么不可以进行修改
                    User user2 = new User();
                    user2.setParentId(user1.getId());
                    List<User> users = userService.myInvitation(user2);
                    if (!users.isEmpty()) {
                        for (User user3 : users) {
                            //下级的有上级收益比例+目前准备设置的代理比例超过100，就不可以进行修改
                            BigDecimal result = user3.getHaveManagerRatio().add(user.getCommanderRatio());
                            if (result.compareTo(new BigDecimal(100)) > 0) {
                                return AjaxResult.error(user3.getNickName() + " 的有上级比例为：" + user3.getHaveManagerRatio() + ";和您设置的比例总和大于100，修改失败！");
                            }
                        }
                    }
                    user1.setCommanderRatio(user.getCommanderRatio());
                    userService.updateUser(user1);
                }
            }
        }
        if (sysUserService.updateUserProfile(user) > 0) {
            // 更新缓存用户信息
            sysUser.setNickName(user.getNickName());
            sysUser.setPhonenumber(user.getPhonenumber());
            sysUser.setEmail(user.getEmail());
            sysUser.setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        //查询修改的比例是不是符合要求
        return AjaxResult.error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = getLoginUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return AjaxResult.error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return AjaxResult.error("新密码不能与旧密码相同");
        }
        if (sysUserService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword), newPassword) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            LoginUser loginUser = getLoginUser();
            String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
            if (sysUserService.updateUserAvatar(loginUser.getUsername(), avatar)) {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", avatar);
                // 更新缓存用户头像
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                return ajax;
            }
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
}
