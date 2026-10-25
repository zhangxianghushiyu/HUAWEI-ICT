package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaskDailyLookVideoToCoinMapper;
import com.ruoyi.platform.domain.TaskDailyLookVideoToCoin;
import com.ruoyi.platform.service.ITaskDailyLookVideoToCoinService;

/**
 * 看视频领金币配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-22
 */
@Service
public class TaskDailyLookVideoToCoinServiceImpl implements ITaskDailyLookVideoToCoinService 
{
    @Autowired
    private TaskDailyLookVideoToCoinMapper taskDailyLookVideoToCoinMapper;

    /**
     * 查询看视频领金币配置
     * 
     * @param id 看视频领金币配置主键
     * @return 看视频领金币配置
     */
    @Override
    public TaskDailyLookVideoToCoin selectTaskDailyLookVideoToCoinById(Long id)
    {
        return taskDailyLookVideoToCoinMapper.selectTaskDailyLookVideoToCoinById(id);
    }

    /**
     * 查询看视频领金币配置列表
     * 
     * @param taskDailyLookVideoToCoin 看视频领金币配置
     * @return 看视频领金币配置
     */
    @Override
    public List<TaskDailyLookVideoToCoin> selectTaskDailyLookVideoToCoinList(TaskDailyLookVideoToCoin taskDailyLookVideoToCoin)
    {
        return taskDailyLookVideoToCoinMapper.selectTaskDailyLookVideoToCoinList(taskDailyLookVideoToCoin);
    }

    /**
     * 新增看视频领金币配置
     * 
     * @param taskDailyLookVideoToCoin 看视频领金币配置
     * @return 结果
     */
    @Override
    public int insertTaskDailyLookVideoToCoin(TaskDailyLookVideoToCoin taskDailyLookVideoToCoin)
    {
        taskDailyLookVideoToCoin.setCreateTime(DateUtils.getNowDate());
        return taskDailyLookVideoToCoinMapper.insertTaskDailyLookVideoToCoin(taskDailyLookVideoToCoin);
    }

    /**
     * 修改看视频领金币配置
     * 
     * @param taskDailyLookVideoToCoin 看视频领金币配置
     * @return 结果
     */
    @Override
    public int updateTaskDailyLookVideoToCoin(TaskDailyLookVideoToCoin taskDailyLookVideoToCoin)
    {
        taskDailyLookVideoToCoin.setUpdateTime(DateUtils.getNowDate());
        return taskDailyLookVideoToCoinMapper.updateTaskDailyLookVideoToCoin(taskDailyLookVideoToCoin);
    }

    /**
     * 批量删除看视频领金币配置
     * 
     * @param ids 需要删除的看视频领金币配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailyLookVideoToCoinByIds(Long[] ids)
    {
        return taskDailyLookVideoToCoinMapper.deleteTaskDailyLookVideoToCoinByIds(ids);
    }

    /**
     * 删除看视频领金币配置信息
     * 
     * @param id 看视频领金币配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailyLookVideoToCoinById(Long id)
    {
        return taskDailyLookVideoToCoinMapper.deleteTaskDailyLookVideoToCoinById(id);
    }
}
