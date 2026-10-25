package com.ruoyi.platform.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.TApplyAgent;
import com.ruoyi.platform.mapper.TApplyAgentDao;
import com.ruoyi.platform.service.ITApplyAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 申请代理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-02
 */
@Service
public class TApplyAgentServiceImpl implements ITApplyAgentService
{
    @Autowired
    private TApplyAgentDao tApplyAgentMapper;

    /**
     * 查询申请代理
     * 
     * @param id 申请代理主键
     * @return 申请代理
     */
    @Override
    public TApplyAgent selectTApplyAgentById(Long id)
    {
        return tApplyAgentMapper.selectTApplyAgentById(id);
    }

    /**
     * 查询申请代理列表
     * 
     * @param tApplyAgent 申请代理
     * @return 申请代理
     */
    @Override
    public List<TApplyAgent> selectTApplyAgentList(TApplyAgent tApplyAgent)
    {
        return tApplyAgentMapper.selectTApplyAgentList(tApplyAgent);
    }

    /**
     * 新增申请代理
     * 
     * @param tApplyAgent 申请代理
     * @return 结果
     */
    @Override
    public int insertTApplyAgent(TApplyAgent tApplyAgent)
    {
        tApplyAgent.setCreateTime(DateUtils.getNowDate());
        return tApplyAgentMapper.insertTApplyAgent(tApplyAgent);
    }

    /**
     * 修改申请代理
     * 
     * @param tApplyAgent 申请代理
     * @return 结果
     */
    @Override
    public int updateTApplyAgent(TApplyAgent tApplyAgent)
    {
        tApplyAgent.setUpdateTime(DateUtils.getNowDate());
        return tApplyAgentMapper.updateTApplyAgent(tApplyAgent);
    }

    /**
     * 批量删除申请代理
     * 
     * @param ids 需要删除的申请代理主键
     * @return 结果
     */
    @Override
    public int deleteTApplyAgentByIds(Long[] ids)
    {
        return tApplyAgentMapper.deleteTApplyAgentByIds(ids);
    }

    /**
     * 删除申请代理信息
     * 
     * @param id 申请代理主键
     * @return 结果
     */
    @Override
    public int deleteTApplyAgentById(Long id)
    {
        return tApplyAgentMapper.deleteTApplyAgentById(id);
    }
}
