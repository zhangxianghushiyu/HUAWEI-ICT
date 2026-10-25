package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.IncomeWay;

/**
 * 收益方式Mapper接口
 * 
 * @author ruoyi
 * @date 2025-05-15
 */
public interface IncomeWayMapper 
{
    /**
     * 查询收益方式
     * 
     * @param id 收益方式主键
     * @return 收益方式
     */
    public IncomeWay selectIncomeWayById(Long id);

    /**
     * 查询收益方式列表
     * 
     * @param incomeWay 收益方式
     * @return 收益方式集合
     */
    public List<IncomeWay> selectIncomeWayList(IncomeWay incomeWay);

    /**
     * 新增收益方式
     * 
     * @param incomeWay 收益方式
     * @return 结果
     */
    public int insertIncomeWay(IncomeWay incomeWay);

    /**
     * 修改收益方式
     * 
     * @param incomeWay 收益方式
     * @return 结果
     */
    public int updateIncomeWay(IncomeWay incomeWay);

    /**
     * 删除收益方式
     * 
     * @param id 收益方式主键
     * @return 结果
     */
    public int deleteIncomeWayById(Long id);

    /**
     * 批量删除收益方式
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIncomeWayByIds(Long[] ids);
}
