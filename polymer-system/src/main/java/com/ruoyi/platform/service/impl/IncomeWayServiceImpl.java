package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.IncomeWayMapper;
import com.ruoyi.platform.domain.IncomeWay;
import com.ruoyi.platform.service.IIncomeWayService;

/**
 * 收益方式Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-15
 */
@Service
public class IncomeWayServiceImpl implements IIncomeWayService 
{
    @Autowired
    private IncomeWayMapper incomeWayMapper;

    /**
     * 查询收益方式
     * 
     * @param id 收益方式主键
     * @return 收益方式
     */
    @Override
    public IncomeWay selectIncomeWayById(Long id)
    {
        return incomeWayMapper.selectIncomeWayById(id);
    }

    /**
     * 查询收益方式列表
     * 
     * @param incomeWay 收益方式
     * @return 收益方式
     */
    @Override
    public List<IncomeWay> selectIncomeWayList(IncomeWay incomeWay)
    {
        return incomeWayMapper.selectIncomeWayList(incomeWay);
    }

    /**
     * 新增收益方式
     * 
     * @param incomeWay 收益方式
     * @return 结果
     */
    @Override
    public int insertIncomeWay(IncomeWay incomeWay)
    {
        incomeWay.setCreateTime(DateUtils.getNowDate());
        return incomeWayMapper.insertIncomeWay(incomeWay);
    }

    /**
     * 修改收益方式
     * 
     * @param incomeWay 收益方式
     * @return 结果
     */
    @Override
    public int updateIncomeWay(IncomeWay incomeWay)
    {
        incomeWay.setUpdateTime(DateUtils.getNowDate());
        return incomeWayMapper.updateIncomeWay(incomeWay);
    }

    /**
     * 批量删除收益方式
     * 
     * @param ids 需要删除的收益方式主键
     * @return 结果
     */
    @Override
    public int deleteIncomeWayByIds(Long[] ids)
    {
        return incomeWayMapper.deleteIncomeWayByIds(ids);
    }

    /**
     * 删除收益方式信息
     * 
     * @param id 收益方式主键
     * @return 结果
     */
    @Override
    public int deleteIncomeWayById(Long id)
    {
        return incomeWayMapper.deleteIncomeWayById(id);
    }
}
