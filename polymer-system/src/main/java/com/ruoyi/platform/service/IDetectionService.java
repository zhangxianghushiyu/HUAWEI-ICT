package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.Detection;

/**
 * 风控管理Service接口
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
public interface IDetectionService 
{
    /**
     * 查询风控管理
     * 
     * @param id 风控管理主键
     * @return 风控管理
     */
    public Detection selectDetectionById(Long id);

    /**
     * 查询风控管理列表
     * 
     * @param detection 风控管理
     * @return 风控管理集合
     */
    public List<Detection> selectDetectionList(Detection detection);

    /**
     * 新增风控管理
     * 
     * @param detection 风控管理
     * @return 结果
     */
    public int insertDetection(Detection detection);

    /**
     * 修改风控管理
     * 
     * @param detection 风控管理
     * @return 结果
     */
    public int updateDetection(Detection detection);

    /**
     * 批量删除风控管理
     * 
     * @param ids 需要删除的风控管理主键集合
     * @return 结果
     */
    public int deleteDetectionByIds(Long[] ids);

    /**
     * 删除风控管理信息
     * 
     * @param id 风控管理主键
     * @return 结果
     */
    public int deleteDetectionById(Long id);

    Integer updateValueIsNull(Long id);
}
