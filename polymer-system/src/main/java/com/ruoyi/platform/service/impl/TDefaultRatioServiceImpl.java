package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.TDefaultRatio;
import com.ruoyi.platform.mapper.TDefaultRatioMapper;
import com.ruoyi.platform.service.ITDefaultRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 统一默认比例Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-03
 */
@Service
public class TDefaultRatioServiceImpl implements ITDefaultRatioService
{
    @Autowired
    private TDefaultRatioMapper tDefaultRatioMapper;

    /**
     * 查询统一默认比例
     * 
     * @param id 统一默认比例主键
     * @return 统一默认比例
     */
    @Override
    public TDefaultRatio selectTDefaultRatioById(Long id)
    {
        return tDefaultRatioMapper.selectTDefaultRatioById(id);
    }

    /**
     * 查询统一默认比例列表
     * 
     * @param tDefaultRatio 统一默认比例
     * @return 统一默认比例
     */
    @Override
    public List<TDefaultRatio> selectTDefaultRatioList(TDefaultRatio tDefaultRatio)
    {
        return tDefaultRatioMapper.selectTDefaultRatioList(tDefaultRatio);
    }

    /**
     * 新增统一默认比例
     * 
     * @param tDefaultRatio 统一默认比例
     * @return 结果
     */
    @Override
    public int insertTDefaultRatio(TDefaultRatio tDefaultRatio)
    {
        tDefaultRatio.setCreateTime(DateUtils.getNowDate());
        return tDefaultRatioMapper.insertTDefaultRatio(tDefaultRatio);
    }

    /**
     * 修改统一默认比例
     * 
     * @param tDefaultRatio 统一默认比例
     * @return 结果
     */
    @Override
    public int updateTDefaultRatio(TDefaultRatio tDefaultRatio)
    {
        tDefaultRatio.setUpdateTime(DateUtils.getNowDate());
        return tDefaultRatioMapper.updateTDefaultRatio(tDefaultRatio);
    }

    /**
     * 批量删除统一默认比例
     * 
     * @param ids 需要删除的统一默认比例主键
     * @return 结果
     */
    @Override
    public int deleteTDefaultRatioByIds(Long[] ids)
    {
        return tDefaultRatioMapper.deleteTDefaultRatioByIds(ids);
    }

    /**
     * 删除统一默认比例信息
     * 
     * @param id 统一默认比例主键
     * @return 结果
     */
    @Override
    public int deleteTDefaultRatioById(Long id)
    {
        return tDefaultRatioMapper.deleteTDefaultRatioById(id);
    }
}
