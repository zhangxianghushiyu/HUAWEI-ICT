package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.WithdrawalInterval;

/**
 * 提现间隔配置Mapper接口
 * 
 * @author ruoyi
 * @date 2024-12-03
 */
public interface WithdrawalIntervalMapper 
{
    /**
     * 查询提现间隔配置
     * 
     * @param id 提现间隔配置主键
     * @return 提现间隔配置
     */
    public WithdrawalInterval selectWithdrawalIntervalById(Long id);

    /**
     * 查询提现间隔配置列表
     * 
     * @param withdrawalInterval 提现间隔配置
     * @return 提现间隔配置集合
     */
    public List<WithdrawalInterval> selectWithdrawalIntervalList(WithdrawalInterval withdrawalInterval);

    /**
     * 新增提现间隔配置
     * 
     * @param withdrawalInterval 提现间隔配置
     * @return 结果
     */
    public int insertWithdrawalInterval(WithdrawalInterval withdrawalInterval);

    /**
     * 修改提现间隔配置
     * 
     * @param withdrawalInterval 提现间隔配置
     * @return 结果
     */
    public int updateWithdrawalInterval(WithdrawalInterval withdrawalInterval);

    /**
     * 删除提现间隔配置
     * 
     * @param id 提现间隔配置主键
     * @return 结果
     */
    public int deleteWithdrawalIntervalById(Long id);

    /**
     * 批量删除提现间隔配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWithdrawalIntervalByIds(Long[] ids);
}
