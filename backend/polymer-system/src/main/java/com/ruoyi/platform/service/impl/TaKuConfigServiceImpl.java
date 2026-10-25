package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaKuConfigMapper;
import com.ruoyi.platform.domain.TaKuConfig;
import com.ruoyi.platform.service.ITaKuConfigService;

/**
 * taKu广告配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-27
 */
@Service
public class TaKuConfigServiceImpl implements ITaKuConfigService 
{
    @Autowired
    private TaKuConfigMapper taKuConfigMapper;

    /**
     * 查询taKu广告配置
     * 
     * @param id taKu广告配置主键
     * @return taKu广告配置
     */
    @Override
    public TaKuConfig selectTaKuConfigById(Long id)
    {
        return taKuConfigMapper.selectTaKuConfigById(id);
    }

    /**
     * 查询taKu广告配置列表
     * 
     * @param taKuConfig taKu广告配置
     * @return taKu广告配置
     */
    @Override
    public List<TaKuConfig> selectTaKuConfigList(TaKuConfig taKuConfig)
    {
        return taKuConfigMapper.selectTaKuConfigList(taKuConfig);
    }

    /**
     * 新增taKu广告配置
     * 
     * @param taKuConfig taKu广告配置
     * @return 结果
     */
    @Override
    public int insertTaKuConfig(TaKuConfig taKuConfig)
    {
        taKuConfig.setCreateTime(DateUtils.getNowDate());
        return taKuConfigMapper.insertTaKuConfig(taKuConfig);
    }

    /**
     * 修改taKu广告配置
     * 
     * @param taKuConfig taKu广告配置
     * @return 结果
     */
    @Override
    public int updateTaKuConfig(TaKuConfig taKuConfig)
    {
        taKuConfig.setUpdateTime(DateUtils.getNowDate());
        return taKuConfigMapper.updateTaKuConfig(taKuConfig);
    }

    /**
     * 批量删除taKu广告配置
     * 
     * @param ids 需要删除的taKu广告配置主键
     * @return 结果
     */
    @Override
    public int deleteTaKuConfigByIds(Long[] ids)
    {
        return taKuConfigMapper.deleteTaKuConfigByIds(ids);
    }

    /**
     * 删除taKu广告配置信息
     * 
     * @param id taKu广告配置主键
     * @return 结果
     */
    @Override
    public int deleteTaKuConfigById(Long id)
    {
        return taKuConfigMapper.deleteTaKuConfigById(id);
    }
}
