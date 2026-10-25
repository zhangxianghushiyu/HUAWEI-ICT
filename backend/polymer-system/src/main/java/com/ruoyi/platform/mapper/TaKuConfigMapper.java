package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.TaKuConfig;

/**
 * taKu广告配置Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-27
 */
public interface TaKuConfigMapper 
{
    /**
     * 查询taKu广告配置
     * 
     * @param id taKu广告配置主键
     * @return taKu广告配置
     */
    public TaKuConfig selectTaKuConfigById(Long id);

    /**
     * 查询taKu广告配置列表
     * 
     * @param taKuConfig taKu广告配置
     * @return taKu广告配置集合
     */
    public List<TaKuConfig> selectTaKuConfigList(TaKuConfig taKuConfig);

    /**
     * 新增taKu广告配置
     * 
     * @param taKuConfig taKu广告配置
     * @return 结果
     */
    public int insertTaKuConfig(TaKuConfig taKuConfig);

    /**
     * 修改taKu广告配置
     * 
     * @param taKuConfig taKu广告配置
     * @return 结果
     */
    public int updateTaKuConfig(TaKuConfig taKuConfig);

    /**
     * 删除taKu广告配置
     * 
     * @param id taKu广告配置主键
     * @return 结果
     */
    public int deleteTaKuConfigById(Long id);

    /**
     * 批量删除taKu广告配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaKuConfigByIds(Long[] ids);
}
