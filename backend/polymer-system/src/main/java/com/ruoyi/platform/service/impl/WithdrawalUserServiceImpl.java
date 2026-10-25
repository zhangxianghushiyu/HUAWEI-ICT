package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.WithdrawalUserMapper;
import com.ruoyi.platform.domain.WithdrawalUser;
import com.ruoyi.platform.service.IWithdrawalUserService;

/**
 * 用户提现绑定信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
@Service
public class WithdrawalUserServiceImpl implements IWithdrawalUserService 
{
    @Autowired
    private WithdrawalUserMapper withdrawalUserMapper;

    /**
     * 查询用户提现绑定信息
     * 
     * @param id 用户提现绑定信息主键
     * @return 用户提现绑定信息
     */
    @Override
    public WithdrawalUser selectWithdrawalUserById(Long id)
    {
        return withdrawalUserMapper.selectWithdrawalUserById(id);
    }

    /**
     * 查询用户提现绑定信息列表
     * 
     * @param withdrawalUser 用户提现绑定信息
     * @return 用户提现绑定信息
     */
    @Override
    public List<WithdrawalUser> selectWithdrawalUserList(WithdrawalUser withdrawalUser)
    {
        return withdrawalUserMapper.selectWithdrawalUserList(withdrawalUser);
    }

    /**
     * 新增用户提现绑定信息
     * 
     * @param withdrawalUser 用户提现绑定信息
     * @return 结果
     */
    @Override
    public int insertWithdrawalUser(WithdrawalUser withdrawalUser)
    {
        withdrawalUser.setCreateTime(DateUtils.getNowDate());
        return withdrawalUserMapper.insertWithdrawalUser(withdrawalUser);
    }

    /**
     * 修改用户提现绑定信息
     * 
     * @param withdrawalUser 用户提现绑定信息
     * @return 结果
     */
    @Override
    public int updateWithdrawalUser(WithdrawalUser withdrawalUser)
    {
        withdrawalUser.setUpdateTime(DateUtils.getNowDate());
        return withdrawalUserMapper.updateWithdrawalUser(withdrawalUser);
    }

    /**
     * 批量删除用户提现绑定信息
     * 
     * @param ids 需要删除的用户提现绑定信息主键
     * @return 结果
     */
    @Override
    public int deleteWithdrawalUserByIds(Long[] ids)
    {
        return withdrawalUserMapper.deleteWithdrawalUserByIds(ids);
    }

    /**
     * 删除用户提现绑定信息信息
     * 
     * @param id 用户提现绑定信息主键
     * @return 结果
     */
    @Override
    public int deleteWithdrawalUserById(Long id)
    {
        return withdrawalUserMapper.deleteWithdrawalUserById(id);
    }
}
