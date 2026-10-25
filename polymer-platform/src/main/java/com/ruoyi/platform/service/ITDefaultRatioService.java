package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.TDefaultRatio;

import java.util.List;

/**
 * 统一默认比例Service接口
 * 
 * @author ruoyi
 * @date 2025-07-03
 */
public interface ITDefaultRatioService 
{
    /**
     * 查询统一默认比例
     * 
     * @param id 统一默认比例主键
     * @return 统一默认比例
     */
    public TDefaultRatio selectTDefaultRatioById(Long id);

    /**
     * 查询统一默认比例列表
     * 
     * @param tDefaultRatio 统一默认比例
     * @return 统一默认比例集合
     */
    public List<TDefaultRatio> selectTDefaultRatioList(TDefaultRatio tDefaultRatio);

    /**
     * 新增统一默认比例
     * 
     * @param tDefaultRatio 统一默认比例
     * @return 结果
     */
    public int insertTDefaultRatio(TDefaultRatio tDefaultRatio);

    /**
     * 修改统一默认比例
     * 
     * @param tDefaultRatio 统一默认比例
     * @return 结果
     */
    public int updateTDefaultRatio(TDefaultRatio tDefaultRatio);

    /**
     * 批量删除统一默认比例
     * 
     * @param ids 需要删除的统一默认比例主键集合
     * @return 结果
     */
    public int deleteTDefaultRatioByIds(Long[] ids);

    /**
     * 删除统一默认比例信息
     * 
     * @param id 统一默认比例主键
     * @return 结果
     */
    public int deleteTDefaultRatioById(Long id);
}
