package com.ruoyi.platform.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.util.RandomUtil;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.ParamsConfigTypeEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.EncryptUtils;
import com.ruoyi.platform.domain.UserGrade;
import com.ruoyi.platform.mapper.UserGradeMapper;
import com.ruoyi.platform.service.IParamsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.UserMapper;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.service.IUserService;

/**
 * 用户信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-08-10
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IParamsConfigService paramsConfigService;
    @Autowired
    private UserGradeMapper userGradeMapper;

    /**
     * 查询用户信息
     *
     * @param id 用户信息主键
     * @return 用户信息
     */
    @Override
    public User selectUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    /**
     * 查询用户信息列表
     *
     * @param user 用户信息
     * @return 用户信息
     */
    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }


    @Override
    public List<User> selectUserListJunior(User user) {
        return userMapper.selectUserListJunior(user);
    }

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public AjaxResult insertUser(User user) {
        //查询账号有没有重复
        User users = userMapper.selectUserByUserNameNoUnsubscribe(user.getUserName());
        if (Objects.nonNull(users)) {
            return AjaxResult.error("已存在相同的账号");
        }
        //生成6位随机数(数字)
        String code = RandomUtil.randomNumbers(6);
        //查询是否有重复的信息
        User user2 = userMapper.selectUserByInvitationCode(code);
        while (Objects.nonNull(user2)) {
            //重新生成
            code = RandomUtil.randomNumbers(6);
            //再次查询
            user2 = userMapper.selectUserByInvitationCode(code);
        }
        Date nowDate = DateUtils.getNowDate();
        user.setInvitationCode(code);
        user.setNickName(paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_6.getType())
                .get(0).getValue() + "_"
                + RandomUtil.randomNumbers(4));
        user.setAvatar(paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_5.getType()).get(0).getValue());
        user.setCreateTime(nowDate);
        //加密,生成一个盐
        String salt = RandomUtil.randomString(16);
        user.setSalt(salt);
        user.setPassword(EncryptUtils.passwordEncryption(user.getPassword(), salt));
        userMapper.insertUser(user);
        //初始化用户阶级记录
        UserGrade userGrade = new UserGrade();
        userGrade.setUserId(user.getId().intValue());
        //默认已通过0
        userGrade.setBarrier(0);

        //目标等级是2
        userGrade.setGrade(2);
        userGrade.setCreateTime(nowDate);
        userGradeMapper.insertUserGrade(userGrade);
        return AjaxResult.success();
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Integer[] ids) {
        return userMapper.deleteUserByIds(ids);
    }

    /**
     * 删除用户信息信息
     *
     * @param id 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }


    @Override
    public Integer modifyUserStatus(Integer userId, Integer status) {
        return userMapper.updateUserStatus(userId, status);
    }


    @Override
    public List<User> myInvitation(User user) {
        return userMapper.myInvitation(user);
    }

    @Override
    public Integer modifyRestore(Long userId) {
        return userMapper.modifyRestore(userId);
    }

    @Override
    public Integer updateNoCommander(Integer id) {
        return userMapper.updateNoCommander(id);
    }
}
