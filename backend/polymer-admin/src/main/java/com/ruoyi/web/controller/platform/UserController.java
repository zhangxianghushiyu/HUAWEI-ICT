package com.ruoyi.web.controller.platform;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.UserStatusEnum;
import com.ruoyi.common.utils.EncryptUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.service.IUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户信息Controller
 *
 * @author ruoyi
 * @date 2023-08-10
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/platform/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询用户信息列表
     */
    @ApiOperation("查询用户信息列表")
    @PreAuthorize("@ss.hasPermi('platform:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(User user) {
        Long userId = SecurityUtils.getUserId();
        boolean admin = SecurityUtils.isAdmin(userId);
        List<User> list;
        if (admin) {
            startPage();
            list = userService.selectUserList(user);
        } else {
            SysUser sysUser = sysUserService.selectUserById(userId);
            if (Objects.isNull(sysUser.getBindUserId())) {
                throw new RuntimeException("请绑定客户端用户ID");
            } else {
                //查询自己的下级用户列表
                startPage();
                user.setParentId(sysUser.getBindUserId().longValue());
                list = userService.selectUserListJunior(user);
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:user:export')")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, User user) {
        List<User> list = userService.selectUserList(user);
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        util.exportExcel(response, list, "用户信息数据");
    }

    /**
     * 获取用户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(userService.selectUserById(id));
    }

    /**
     * 新增用户信息
     */
    @PreAuthorize("@ss.hasPermi('platform:user:add')")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody User user) {
        return userService.insertUser(user);
    }

    /**
     * 修改用户信息
     */
    @PreAuthorize("@ss.hasPermi('platform:user:edit')")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody User user) {
        if (2 == user.getGetType()) {
            //默认值
            if (user.getGoldMax().compareTo(user.getGoldMin()) < 1) {
                return AjaxResult.error("金额值输入不正确");
            }
        } else if (1 == user.getGetType()) {
            //默认值
            user.setGoldMin(new BigDecimal(0));
            user.setGoldMax(new BigDecimal(0));
        }
        if (2 == user.getJuniorGetType()) {
            //默认值
            if (user.getJuniorGoldMax().compareTo(user.getJuniorGoldMin()) < 1) {
                return AjaxResult.error("金额值输入不正确");
            }
        } else if (1 == user.getJuniorGetType()) {
            //默认值
            user.setJuniorGoldMin(new BigDecimal(0));
            user.setJuniorGoldMax(new BigDecimal(0));
        }
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户信息
     */
    @PreAuthorize("@ss.hasPermi('platform:user:remove')")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(userService.deleteUserByIds(ids));
    }


    /**
     * 修改用户状态（针对停用操作）
     */
    @GetMapping("/modifyUserStatusDeactivate")
    public AjaxResult modifyUserStatusDeactivate(Long userId) {
        User user = userService.selectUserById(userId);
        if (Objects.nonNull(user)) {
            user.setStatus(user.getStatus().equals(UserStatusEnum.STATUS_0.getStatus()) ?
                    UserStatusEnum.STATUS_1.getStatus() : UserStatusEnum.STATUS_0.getStatus());
            userService.updateUser(user);
            //删除redis中的值
            redisCache.deleteObject(Constants.USER_LOGIN_TOKEN + userId);
        }
        return AjaxResult.success();
    }


    /**
     * 修改用户代理信息
     */
    @GetMapping("/setCommanderInfo")
    @Transactional
    public AjaxResult setCommanderInfo(Long userId) {
        User user = userService.selectUserById(userId);
        if (Objects.nonNull(user)) {
            user.setCommander(user.getCommander() == 1 ?
                    0 : 1);
            if (user.getCommander() == 0) {
                user.setCommanderRatio(BigDecimal.ZERO);
                user.setGetType(1);
                user.setGoldMin(BigDecimal.ZERO);
                user.setGoldMax(BigDecimal.ZERO);
                user.setJuniorGetType(1);
                user.setJuniorGoldMin(BigDecimal.ZERO);
                user.setJuniorGoldMax(BigDecimal.ZERO);
            }else if (user.getCommander() == 1){
                user.setCommanderRatio(new BigDecimal(20));
            }
            userService.updateUser(user);
            if (user.getCommander() == 1) {
                //修改为代理，将此用户的上级全部清空
                userService.updateNoCommander(user.getId().intValue());
            }
        }
        return AjaxResult.success();
    }


    /**
     * 我的邀请列表
     */
    @GetMapping("/invitationInfo")
    public TableDataInfo invitationInfo(User user) {
        startPage();
        List<User> list = userService.myInvitation(user);
        return getDataTable(list);
    }

    /**
     * 重置用户密码
     */
    @GetMapping(value = "/resetPassword")
    public AjaxResult resetPassword(@RequestParam(value = "userId") Long userId,
                                    @RequestParam(value = "password") String password) {
        User user = userService.selectUserById(userId);
        user.setPassword(EncryptUtils.passwordEncryption(password, user.getSalt()));
        userService.updateUser(user);
        return AjaxResult.success();
    }


    /**
     * 设置代理比例
     */
    @GetMapping(value = "/setCommanderValue")
    public AjaxResult setCommanderValue(@RequestParam(value = "userId") Long userId,
                                        @RequestParam(value = "value") BigDecimal value) {
        double v = value.doubleValue();
        if (v < 0 || v > 100) {
            return AjaxResult.error("请输入0-100之间的值");
        }
        User user = userService.selectUserById(userId);
        //查询该用户的所有下级，是不是和代理比例计算之后超过100，如果超过100，那么不可以进行修改
//        User user2 = new User();
//        user2.setParentId(user.getId());
//        List<User> users = userService.myInvitation(user2);
//        if (!users.isEmpty()) {
//            for (User user3 : users) {
//                //下级的有上级收益比例+目前准备设置的代理比例超过100，就不可以进行修改
//                BigDecimal result = user3.getHaveManagerRatio().add(value);
//                if (result.compareTo(new BigDecimal(100)) > 0) {
//                    return AjaxResult.error(user3.getNickName() + " 的有上级比例为：" + user3.getHaveManagerRatio() + ";和您设置的比例总和大于100，修改失败！");
//                }
//            }
//        }
        user.setCommanderRatio(value);
        userService.updateUser(user);
        return AjaxResult.success();
    }

    /**
     * 设置有上级比例
     */
    @GetMapping(value = "/setHaveManagerRatio")
    public AjaxResult haveManagerRatio(@RequestParam(value = "userId") Long userId,
                                       @RequestParam(value = "value") BigDecimal value) {
        double v = value.doubleValue();
        if (v < 0 || v > 100) {
            return AjaxResult.error("请输入0-100之间的值");
        }
        User user = userService.selectUserById(userId);
        //查询这个用户是不是有上级，他的上级比例代理+自己的有上级比例不可以超过100
//        if (Objects.nonNull(user) && Objects.nonNull(user.getParentId())) {
//            User user1 = userService.selectUserById(user.getParentId());
//            if (Objects.nonNull(user1)) {
//                BigDecimal result = user1.getCommanderRatio().add(value);
//                if (result.compareTo(new BigDecimal(100)) > 0) {
//                    return AjaxResult.error(user.getNickName()+"有上级，上级比例：" + user1.getCommanderRatio() + ";和您设置的比例总和大于100，修改失败！");
//                }
//            }
//        }
        user.setHaveManagerRatio(value);
        userService.updateUser(user);
        return AjaxResult.success();
    }

    /**
     * 设置无上级比例
     */
    @GetMapping(value = "/setNoManagerRatio")
    public AjaxResult noManagerRatio(@RequestParam(value = "userId") Long userId,
                                     @RequestParam(value = "value") BigDecimal value) {
        double v = value.doubleValue();
        if (v < 0 || v > 100) {
            return AjaxResult.error("请输入0-100之间的值");
        }
        User user = userService.selectUserById(userId);
        user.setNoManagerRatio(value);
        userService.updateUser(user);
        return AjaxResult.success();
    }

    /**
     * 重置用户邀请码
     */
    @GetMapping(value = "/resetInvitationCode")
    public AjaxResult resetInvitationCode(@RequestParam(value = "userId") Long userId,
                                          @RequestParam(value = "invitationCode") String invitationCode) {
        User user = userService.selectUserById(userId);
        //查询该邀请码是不是已经存在
        User user1 = new User();
        user1.setInvitationCode(invitationCode);
        List<User> users = userService.selectUserList(user1);
        if (Objects.nonNull(users) && !users.isEmpty()) {
            return AjaxResult.error("已存在相同的邀请码");
        }
        user.setInvitationCode(invitationCode);
        userService.updateUser(user);
        return AjaxResult.success();
    }


    /**
     * 设置下级是否可以提现
     */
    @GetMapping("/setWithdrawal")
    public AjaxResult setWithdrawal(@RequestParam(value = "id") Long id, @RequestParam(value = "value") Integer value) {
        if (Objects.isNull(value)) {
            return AjaxResult.error("值不能为空");
        }
        if (value < 0 || value > 1) {
            return AjaxResult.error("设置失败，值为0或1之间");
        }
        User user = userService.selectUserById(id);
        userService.updateUser(user);
        return AjaxResult.success();
    }


    /**
     * 设置用户昵称
     */
    @GetMapping("/setUserNickName")
    public AjaxResult setUserNickName(@RequestParam(value = "id") Long id, @RequestParam(value = "nickName") String nickName) {
        User user = userService.selectUserById(id);
        if (Objects.nonNull(user)) {
            user.setNickName(nickName);
            userService.updateUser(user);
        }
        return AjaxResult.success();
    }
}
