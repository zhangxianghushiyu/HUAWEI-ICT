package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.ConvertRatioMapper;
import com.ruoyi.platform.domain.ConvertRatio;
import com.ruoyi.platform.service.IConvertRatioService;

/**
 * 兑换比例Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-12
 */
@Service
public class ConvertRatioServiceImpl implements IConvertRatioService 
{
    @Autowired
    private ConvertRatioMapper convertRatioMapper;

    /**
     * 查询兑换比例
     * 
     * @param id 兑换比例主键
     * @return 兑换比例
     */
    @Override
    public ConvertRatio selectConvertRatioById(Long id)
    {
        return convertRatioMapper.selectConvertRatioById(id);
    }

    /**
     * 查询兑换比例列表
     * 
     * @param convertRatio 兑换比例
     * @return 兑换比例
     */
    @Override
    public List<ConvertRatio> selectConvertRatioList(ConvertRatio convertRatio)
    {
        return convertRatioMapper.selectConvertRatioList(convertRatio);
    }

    /**
     * 新增兑换比例
     * 
     * @param convertRatio 兑换比例
     * @return 结果
     */
    @Override
    public int insertConvertRatio(ConvertRatio convertRatio)
    {
        convertRatio.setCreateTime(DateUtils.getNowDate());
        return convertRatioMapper.insertConvertRatio(convertRatio);
    }

    /**
     * 修改兑换比例
     * 
     * @param convertRatio 兑换比例
     * @return 结果
     */
    @Override
    public int updateConvertRatio(ConvertRatio convertRatio)
    {
        convertRatio.setUpdateTime(DateUtils.getNowDate());
        return convertRatioMapper.updateConvertRatio(convertRatio);
    }

    /**
     * 批量删除兑换比例
     * 
     * @param ids 需要删除的兑换比例主键
     * @return 结果
     */
    @Override
    public int deleteConvertRatioByIds(Long[] ids)
    {
        return convertRatioMapper.deleteConvertRatioByIds(ids);
    }

    /**
     * 删除兑换比例信息
     * 
     * @param id 兑换比例主键
     * @return 结果
     */
    @Override
    public int deleteConvertRatioById(Long id)
    {
        return convertRatioMapper.deleteConvertRatioById(id);
    }
}
