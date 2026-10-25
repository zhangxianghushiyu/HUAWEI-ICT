package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.Barrier;

import java.util.List;

/**
 * 关卡信息Service接口
 * 
 * @author ruoyi
 * @date 2025-02-07
 */
public interface IBarrierService 
{
    /**
     * 查询关卡信息
     * 
     * @param id 关卡信息主键
     * @return 关卡信息
     */
    public Barrier selectBarrierById(Long id);

    /**
     * 查询关卡信息列表
     * 
     * @param barrier 关卡信息
     * @return 关卡信息集合
     */
    public List<Barrier> selectBarrierList(Barrier barrier);

    /**
     * 新增关卡信息
     * 
     * @param barrier 关卡信息
     * @return 结果
     */
    public int insertBarrier(Barrier barrier);

    /**
     * 修改关卡信息
     * 
     * @param barrier 关卡信息
     * @return 结果
     */
    public int updateBarrier(Barrier barrier);

    /**
     * 批量删除关卡信息
     * 
     * @param ids 需要删除的关卡信息主键集合
     * @return 结果
     */
    public int deleteBarrierByIds(Long[] ids);

    /**
     * 删除关卡信息信息
     * 
     * @param id 关卡信息主键
     * @return 结果
     */
    public int deleteBarrierById(Long id);

    /**
     * 根据关卡查询信息
     */
    Barrier selectBarrierByBarrier(Long barrier);
}
