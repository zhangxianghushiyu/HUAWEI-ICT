package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaskDailyAppointDramaCoinInfoMapper;
import com.ruoyi.platform.domain.TaskDailyAppointDramaCoinInfo;
import com.ruoyi.platform.service.ITaskDailyAppointDramaCoinInfoService;

/**
 * 看指定剧领金币配置信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-25
 */
@Service
public class TaskDailyAppointDramaCoinInfoServiceImpl implements ITaskDailyAppointDramaCoinInfoService 
{
    @Autowired
    private TaskDailyAppointDramaCoinInfoMapper taskDailyAppointDramaCoinInfoMapper;

    /**
     * 查询看指定剧领金币配置信息
     * 
     * @param id 看指定剧领金币配置信息主键
     * @return 看指定剧领金币配置信息
     */
    @Override
    public TaskDailyAppointDramaCoinInfo selectTaskDailyAppointDramaCoinInfoById(Long id)
    {
        return taskDailyAppointDramaCoinInfoMapper.selectTaskDailyAppointDramaCoinInfoById(id);
    }

    /**
     * 查询看指定剧领金币配置信息列表
     * 
     * @param taskDailyAppointDramaCoinInfo 看指定剧领金币配置信息
     * @return 看指定剧领金币配置信息
     */
    @Override
    public List<TaskDailyAppointDramaCoinInfo> selectTaskDailyAppointDramaCoinInfoList(TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo)
    {
        return taskDailyAppointDramaCoinInfoMapper.selectTaskDailyAppointDramaCoinInfoList(taskDailyAppointDramaCoinInfo);
    }

    /**
     * 新增看指定剧领金币配置信息
     * 
     * @param taskDailyAppointDramaCoinInfo 看指定剧领金币配置信息
     * @return 结果
     */
    @Override
    public int insertTaskDailyAppointDramaCoinInfo(TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo)
    {
        taskDailyAppointDramaCoinInfo.setCreateTime(DateUtils.getNowDate());
        return taskDailyAppointDramaCoinInfoMapper.insertTaskDailyAppointDramaCoinInfo(taskDailyAppointDramaCoinInfo);
    }

    /**
     * 修改看指定剧领金币配置信息
     * 
     * @param taskDailyAppointDramaCoinInfo 看指定剧领金币配置信息
     * @return 结果
     */
    @Override
    public int updateTaskDailyAppointDramaCoinInfo(TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo)
    {
        taskDailyAppointDramaCoinInfo.setUpdateTime(DateUtils.getNowDate());
        return taskDailyAppointDramaCoinInfoMapper.updateTaskDailyAppointDramaCoinInfo(taskDailyAppointDramaCoinInfo);
    }

    /**
     * 批量删除看指定剧领金币配置信息
     * 
     * @param ids 需要删除的看指定剧领金币配置信息主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailyAppointDramaCoinInfoByIds(Long[] ids)
    {
        return taskDailyAppointDramaCoinInfoMapper.deleteTaskDailyAppointDramaCoinInfoByIds(ids);
    }

    /**
     * 删除看指定剧领金币配置信息信息
     * 
     * @param id 看指定剧领金币配置信息主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailyAppointDramaCoinInfoById(Long id)
    {
        return taskDailyAppointDramaCoinInfoMapper.deleteTaskDailyAppointDramaCoinInfoById(id);
    }
}
