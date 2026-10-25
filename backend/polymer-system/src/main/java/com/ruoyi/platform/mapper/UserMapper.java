package com.ruoyi.platform.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.platform.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-08-10
 */
public interface UserMapper {
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
    public int insertUser(User user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 删除用户信息
     *
     * @param id 用户信息主键
     * @return 结果
     */
    public int deleteUserById(Integer id);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserByIds(Integer[] ids);

    /**
     * 修改用户信息状态
     *
     * @return
     */
    Integer updateUserStatus(@Param("userId") Integer userId, @Param("status") Integer status);


    /**
     * 我的邀请列表
     *
     * @param user 用户信息
     * @return 用户信息集合
     */
    public List<User> myInvitation(User user);


    /**
     * 修改用户会员状态为0
     */
    Integer updateUserVip(@Param("id") Long id);


    /**
     * 加用户现金
     *
     * @param id
     * @return
     */
    Integer addUserCash(@Param("id") Long id, @Param("money") BigDecimal money);

    /**
     * 减用户金币
     *
     * @param id
     * @return
     */
    Integer subGoldCoin(@Param("id") Long id, @Param("money") BigDecimal money);

    /**
     * 恢复被注销的用户
     *
     * @return
     */
    Integer modifyRestore(@Param("id") Long id);


    /**
     * 通过邀请码查询用户
     */
    User selectUserByInvitationCode(@Param("invitationCode") String invitationCode);


    /**
     * 通过账号查询用户（不查注销用户）
     */
    User selectUserByUserNameNoUnsubscribe(@Param("userName") String userName);


    /**
     * 查询满足自动兑换现金的信息
     */
    List<User> selectAutoToCashInfo();

    /**
     * 将用户修改为无上级
     */
    Integer updateNoCommander(@Param("id") Integer id);

    void updateUserStatusByTask();

}
