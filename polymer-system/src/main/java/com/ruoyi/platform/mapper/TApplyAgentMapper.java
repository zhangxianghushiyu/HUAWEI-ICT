package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TApplyAgent;

import java.util.List;

/**
 * 申请代理Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-02
 */
public interface TApplyAgentMapper 
{
    /**
     * 查询申请代理
     * 
     * @param id 申请代理主键
     * @return 申请代理
     */
    public TApplyAgent selectTApplyAgentById(Long id);

    /**
     * 查询申请代理列表
     * 
     * @param tApplyAgent 申请代理
     * @return 申请代理集合
     */
    public List<TApplyAgent> selectTApplyAgentList(TApplyAgent tApplyAgent);

    /**
     * 新增申请代理
     * 
     * @param tApplyAgent 申请代理
     * @return 结果
     */
    public int insertTApplyAgent(TApplyAgent tApplyAgent);

    /**
     * 修改申请代理
     * 
     * @param tApplyAgent 申请代理
     * @return 结果
     */
    public int updateTApplyAgent(TApplyAgent tApplyAgent);

    /**
     * 删除申请代理
     * 
     * @param id 申请代理主键
     * @return 结果
     */
    public int deleteTApplyAgentById(Long id);

    /**
     * 批量删除申请代理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTApplyAgentByIds(Long[] ids);
}
