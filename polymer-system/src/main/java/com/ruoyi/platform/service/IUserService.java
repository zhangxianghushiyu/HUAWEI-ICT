package com.ruoyi.platform.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.platform.domain.User;

/**
 * 用户信息Service接口
 *
 * @author ruoyi
 * @date 2023-08-10
 */
public interface IUserService {
    /**
     * 查询用户信息
     *
     * @param id 用户信息主键
     * @return 用户信息
     */
    public User selectUserById(Long id);

    /**
     * 查询用户信息列表
     *
     * @param user 用户信息
     * @return 用户信息集合
     */
    public List<User> selectUserList(User user);

    /**
     * 查询用户信息列表(查询自己的下级)
     *
     * @param user 用户信息
     * @return 用户信息集合
     */
    public List<User> selectUserListJunior(User user);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public AjaxResult insertUser(User user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的用户信息主键集合
     * @return 结果
     */
    public int deleteUserByIds(Integer[] ids);

    /**
     * 删除用户信息信息
     *
     * @param id 用户信息主键
     * @return 结果
     */
    public int deleteUserById(Integer id);


    /**
     * 修改用户信息状态
     *
     * @return
     */
    Integer modifyUserStatus(Integer userId, Integer status);



    /**
     * 我的邀请列表
     *
     * @param user 用户信息
     * @return 用户信息集合
     */
    public List<User> myInvitation(User user);


    /**
     * 恢复被注销的用户账号
     */
    Integer modifyRestore(Long userId);


    /**
     * 将用户修改为无上级
     */
    Integer updateNoCommander(Integer id);

}
