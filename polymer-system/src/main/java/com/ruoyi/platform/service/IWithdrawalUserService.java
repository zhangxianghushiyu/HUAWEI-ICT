package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.WithdrawalUser;

/**
 * 用户提现绑定信息Service接口
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
public interface IWithdrawalUserService 
{
    /**
     * 查询用户提现绑定信息
     * 
     * @param id 用户提现绑定信息主键
     * @return 用户提现绑定信息
     */
    public WithdrawalUser selectWithdrawalUserById(Long id);

    /**
     * 查询用户提现绑定信息列表
     * 
     * @param withdrawalUser 用户提现绑定信息
     * @return 用户提现绑定信息集合
     */
    public List<WithdrawalUser> selectWithdrawalUserList(WithdrawalUser withdrawalUser);

    /**
     * 新增用户提现绑定信息
     * 
     * @param withdrawalUser 用户提现绑定信息
     * @return 结果
     */
    public int insertWithdrawalUser(WithdrawalUser withdrawalUser);

    /**
     * 修改用户提现绑定信息
     * 
     * @param withdrawalUser 用户提现绑定信息
     * @return 结果
     */
    public int updateWithdrawalUser(WithdrawalUser withdrawalUser);

    /**
     * 批量删除用户提现绑定信息
     * 
     * @param ids 需要删除的用户提现绑定信息主键集合
     * @return 结果
     */
    public int deleteWithdrawalUserByIds(Long[] ids);

    /**
     * 删除用户提现绑定信息信息
     * 
     * @param id 用户提现绑定信息主键
     * @return 结果
     */
    public int deleteWithdrawalUserById(Long id);
}
