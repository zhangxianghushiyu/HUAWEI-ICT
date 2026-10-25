package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.AdConfig;

/**
 * 广告配置Service接口
 * 
 * @author ruoyi
 * @date 2025-01-07
 */
public interface IAdConfigService 
{
    /**
     * 查询广告配置
     * 
     * @param id 广告配置主键
     * @return 广告配置
     */
    public AdConfig selectAdConfigById(Long id);

    /**
     * 查询广告配置列表
     * 
     * @param adConfig 广告配置
     * @return 广告配置集合
     */
    public List<AdConfig> selectAdConfigList(AdConfig adConfig);

    /**
     * 新增广告配置
     * 
     * @param adConfig 广告配置
     * @return 结果
     */
    public int insertAdConfig(AdConfig adConfig);

    /**
     * 修改广告配置
     * 
     * @param adConfig 广告配置
     * @return 结果
     */
    public int updateAdConfig(AdConfig adConfig);

    /**
     * 批量删除广告配置
     * 
     * @param ids 需要删除的广告配置主键集合
     * @return 结果
     */
    public int deleteAdConfigByIds(Long[] ids);

    /**
     * 删除广告配置信息
     * 
     * @param id 广告配置主键
     * @return 结果
     */
    public int deleteAdConfigById(Long id);
}
