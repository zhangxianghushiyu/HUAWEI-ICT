package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.AdConfigMapper;
import com.ruoyi.platform.domain.AdConfig;
import com.ruoyi.platform.service.IAdConfigService;

/**
 * 广告配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-07
 */
@Service
public class AdConfigServiceImpl implements IAdConfigService 
{
    @Autowired
    private AdConfigMapper adConfigMapper;

    /**
     * 查询广告配置
     * 
     * @param id 广告配置主键
     * @return 广告配置
     */
    @Override
    public AdConfig selectAdConfigById(Long id)
    {
        return adConfigMapper.selectAdConfigById(id);
    }

    /**
     * 查询广告配置列表
     * 
     * @param adConfig 广告配置
     * @return 广告配置
     */
    @Override
    public List<AdConfig> selectAdConfigList(AdConfig adConfig)
    {
        return adConfigMapper.selectAdConfigList(adConfig);
    }

    /**
     * 新增广告配置
     * 
     * @param adConfig 广告配置
     * @return 结果
     */
    @Override
    public int insertAdConfig(AdConfig adConfig)
    {
        adConfig.setCreateTime(DateUtils.getNowDate());
        return adConfigMapper.insertAdConfig(adConfig);
    }

    /**
     * 修改广告配置
     * 
     * @param adConfig 广告配置
     * @return 结果
     */
    @Override
    public int updateAdConfig(AdConfig adConfig)
    {
        adConfig.setUpdateTime(DateUtils.getNowDate());
        return adConfigMapper.updateAdConfig(adConfig);
    }

    /**
     * 批量删除广告配置
     * 
     * @param ids 需要删除的广告配置主键
     * @return 结果
     */
    @Override
    public int deleteAdConfigByIds(Long[] ids)
    {
        return adConfigMapper.deleteAdConfigByIds(ids);
    }

    /**
     * 删除广告配置信息
     * 
     * @param id 广告配置主键
     * @return 结果
     */
    @Override
    public int deleteAdConfigById(Long id)
    {
        return adConfigMapper.deleteAdConfigById(id);
    }
}
