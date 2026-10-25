package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.WithdrawalIntervalMapper;
import com.ruoyi.platform.domain.WithdrawalInterval;
import com.ruoyi.platform.service.IWithdrawalIntervalService;

/**
 * 提现间隔配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-12-03
 */
@Service
public class WithdrawalIntervalServiceImpl implements IWithdrawalIntervalService 
{
    @Autowired
    private WithdrawalIntervalMapper withdrawalIntervalMapper;

    /**
     * 查询提现间隔配置
     * 
     * @param id 提现间隔配置主键
     * @return 提现间隔配置
     */
    @Override
    public WithdrawalInterval selectWithdrawalIntervalById(Long id)
    {
        return withdrawalIntervalMapper.selectWithdrawalIntervalById(id);
    }

    /**
     * 查询提现间隔配置列表
     * 
     * @param withdrawalInterval 提现间隔配置
     * @return 提现间隔配置
     */
    @Override
    public List<WithdrawalInterval> selectWithdrawalIntervalList(WithdrawalInterval withdrawalInterval)
    {
        return withdrawalIntervalMapper.selectWithdrawalIntervalList(withdrawalInterval);
    }

    /**
     * 新增提现间隔配置
     * 
     * @param withdrawalInterval 提现间隔配置
     * @return 结果
     */
    @Override
    public int insertWithdrawalInterval(WithdrawalInterval withdrawalInterval)
    {
        withdrawalInterval.setCreateTime(DateUtils.getNowDate());
        return withdrawalIntervalMapper.insertWithdrawalInterval(withdrawalInterval);
    }

    /**
     * 修改提现间隔配置
     * 
     * @param withdrawalInterval 提现间隔配置
     * @return 结果
     */
    @Override
    public int updateWithdrawalInterval(WithdrawalInterval withdrawalInterval)
    {
        withdrawalInterval.setUpdateTime(DateUtils.getNowDate());
        return withdrawalIntervalMapper.updateWithdrawalInterval(withdrawalInterval);
    }

    /**
     * 批量删除提现间隔配置
     * 
     * @param ids 需要删除的提现间隔配置主键
     * @return 结果
     */
    @Override
    public int deleteWithdrawalIntervalByIds(Long[] ids)
    {
        return withdrawalIntervalMapper.deleteWithdrawalIntervalByIds(ids);
    }

    /**
     * 删除提现间隔配置信息
     * 
     * @param id 提现间隔配置主键
     * @return 结果
     */
    @Override
    public int deleteWithdrawalIntervalById(Long id)
    {
        return withdrawalIntervalMapper.deleteWithdrawalIntervalById(id);
    }
}
