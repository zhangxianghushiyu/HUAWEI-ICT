package com.ruoyi.platform.mapper;

import java.util.List;

import com.ruoyi.platform.domain.Detection;
import org.apache.ibatis.annotations.Param;

/**
 * 风控管理Mapper接口
 *
 * @author ruoyi
 * @date 2025-05-21
 */
public interface DetectionMapper {
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
     * 删除风控管理
     *
     * @param id 风控管理主键
     * @return 结果
     */
    public int deleteDetectionById(Long id);

    /**
     * 批量删除风控管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDetectionByIds(Long[] ids);

    Integer updateValueIsNull(@Param("id") Long id);
}
