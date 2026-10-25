package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.WithdrawalTabMapper;
import com.ruoyi.platform.domain.WithdrawalTab;
import com.ruoyi.platform.service.IWithdrawalTabService;

/**
 * 提现金额选项卡Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-14
 */
@Service
public class WithdrawalTabServiceImpl implements IWithdrawalTabService 
{
    @Autowired
    private WithdrawalTabMapper withdrawalTabMapper;

    /**
     * 查询提现金额选项卡
     * 
     * @param id 提现金额选项卡主键
     * @return 提现金额选项卡
     */
    @Override
    public WithdrawalTab selectWithdrawalTabById(Long id)
    {
        return withdrawalTabMapper.selectWithdrawalTabById(id);
    }

    /**
     * 查询提现金额选项卡列表
     * 
     * @param withdrawalTab 提现金额选项卡
     * @return 提现金额选项卡
     */
    @Override
    public List<WithdrawalTab> selectWithdrawalTabList(WithdrawalTab withdrawalTab)
    {
        return withdrawalTabMapper.selectWithdrawalTabList(withdrawalTab);
    }

    /**
     * 新增提现金额选项卡
     * 
     * @param withdrawalTab 提现金额选项卡
     * @return 结果
     */
    @Override
    public int insertWithdrawalTab(WithdrawalTab withdrawalTab)
    {
        withdrawalTab.setCreateTime(DateUtils.getNowDate());
        return withdrawalTabMapper.insertWithdrawalTab(withdrawalTab);
    }

    /**
     * 修改提现金额选项卡
     * 
     * @param withdrawalTab 提现金额选项卡
     * @return 结果
     */
    @Override
    public int updateWithdrawalTab(WithdrawalTab withdrawalTab)
    {
        withdrawalTab.setUpdateTime(DateUtils.getNowDate());
        return withdrawalTabMapper.updateWithdrawalTab(withdrawalTab);
    }

    /**
     * 批量删除提现金额选项卡
     * 
     * @param ids 需要删除的提现金额选项卡主键
     * @return 结果
     */
    @Override
    public int deleteWithdrawalTabByIds(Long[] ids)
    {
        return withdrawalTabMapper.deleteWithdrawalTabByIds(ids);
    }

    /**
     * 删除提现金额选项卡信息
     * 
     * @param id 提现金额选项卡主键
     * @return 结果
     */
    @Override
    public int deleteWithdrawalTabById(Long id)
    {
        return withdrawalTabMapper.deleteWithdrawalTabById(id);
    }
}
