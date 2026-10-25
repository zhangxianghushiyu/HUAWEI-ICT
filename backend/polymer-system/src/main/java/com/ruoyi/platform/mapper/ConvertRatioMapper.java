package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.ConvertRatio;

/**
 * 兑换比例Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-12
 */
public interface ConvertRatioMapper 
{
    /**
     * 查询兑换比例
     * 
     * @param id 兑换比例主键
     * @return 兑换比例
     */
    public ConvertRatio selectConvertRatioById(Long id);

    /**
     * 查询兑换比例列表
     * 
     * @param convertRatio 兑换比例
     * @return 兑换比例集合
     */
    public List<ConvertRatio> selectConvertRatioList(ConvertRatio convertRatio);

    /**
     * 新增兑换比例
     * 
     * @param convertRatio 兑换比例
     * @return 结果
     */
    public int insertConvertRatio(ConvertRatio convertRatio);

    /**
     * 修改兑换比例
     * 
     * @param convertRatio 兑换比例
     * @return 结果
     */
    public int updateConvertRatio(ConvertRatio convertRatio);

    /**
     * 删除兑换比例
     * 
     * @param id 兑换比例主键
     * @return 结果
     */
    public int deleteConvertRatioById(Long id);

    /**
     * 批量删除兑换比例
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConvertRatioByIds(Long[] ids);

    /**
     * 查询比例
     */
    ConvertRatio selectConvertRatioInfo();
}
