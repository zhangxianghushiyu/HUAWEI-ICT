package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.WithdrawalTab;

/**
 * 提现金额选项卡Service接口
 * 
 * @author ruoyi
 * @date 2024-11-14
 */
public interface IWithdrawalTabService 
{
    /**
     * 查询提现金额选项卡
     * 
     * @param id 提现金额选项卡主键
     * @return 提现金额选项卡
     */
    public WithdrawalTab selectWithdrawalTabById(Long id);

    /**
     * 查询提现金额选项卡列表
     * 
     * @param withdrawalTab 提现金额选项卡
     * @return 提现金额选项卡集合
     */
    public List<WithdrawalTab> selectWithdrawalTabList(WithdrawalTab withdrawalTab);

    /**
     * 新增提现金额选项卡
     * 
     * @param withdrawalTab 提现金额选项卡
     * @return 结果
     */
    public int insertWithdrawalTab(WithdrawalTab withdrawalTab);

    /**
     * 修改提现金额选项卡
     * 
     * @param withdrawalTab 提现金额选项卡
     * @return 结果
     */
    public int updateWithdrawalTab(WithdrawalTab withdrawalTab);

    /**
     * 批量删除提现金额选项卡
     * 
     * @param ids 需要删除的提现金额选项卡主键集合
     * @return 结果
     */
    public int deleteWithdrawalTabByIds(Long[] ids);

    /**
     * 删除提现金额选项卡信息
     * 
     * @param id 提现金额选项卡主键
     * @return 结果
     */
    public int deleteWithdrawalTabById(Long id);
}
