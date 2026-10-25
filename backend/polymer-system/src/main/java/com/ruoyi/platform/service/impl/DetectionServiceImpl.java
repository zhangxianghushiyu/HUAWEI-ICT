package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.DetectionMapper;
import com.ruoyi.platform.domain.Detection;
import com.ruoyi.platform.service.IDetectionService;

/**
 * 风控管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
@Service
public class DetectionServiceImpl implements IDetectionService 
{
    @Autowired
    private DetectionMapper detectionMapper;

    /**
     * 查询风控管理
     * 
     * @param id 风控管理主键
     * @return 风控管理
     */
    @Override
    public Detection selectDetectionById(Long id)
    {
        return detectionMapper.selectDetectionById(id);
    }

    /**
     * 查询风控管理列表
     * 
     * @param detection 风控管理
     * @return 风控管理
     */
    @Override
    public List<Detection> selectDetectionList(Detection detection)
    {
        return detectionMapper.selectDetectionList(detection);
    }

    /**
     * 新增风控管理
     * 
     * @param detection 风控管理
     * @return 结果
     */
    @Override
    public int insertDetection(Detection detection)
    {
        detection.setCreateTime(DateUtils.getNowDate());
        return detectionMapper.insertDetection(detection);
    }

    /**
     * 修改风控管理
     * 
     * @param detection 风控管理
     * @return 结果
     */
    @Override
    public int updateDetection(Detection detection)
    {
        detection.setUpdateTime(DateUtils.getNowDate());
        return detectionMapper.updateDetection(detection);
    }

    /**
     * 批量删除风控管理
     * 
     * @param ids 需要删除的风控管理主键
     * @return 结果
     */
    @Override
    public int deleteDetectionByIds(Long[] ids)
    {
        return detectionMapper.deleteDetectionByIds(ids);
    }

    /**
     * 删除风控管理信息
     * 
     * @param id 风控管理主键
     * @return 结果
     */
    @Override
    public int deleteDetectionById(Long id)
    {
        return detectionMapper.deleteDetectionById(id);
    }

    @Override
    public Integer updateValueIsNull(Long id) {
        return detectionMapper.updateValueIsNull(id);
    }
}
