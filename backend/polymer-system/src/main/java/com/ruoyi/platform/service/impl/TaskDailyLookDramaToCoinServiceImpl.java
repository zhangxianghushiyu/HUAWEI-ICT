package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaskDailyLookDramaToCoinMapper;
import com.ruoyi.platform.domain.TaskDailyLookDramaToCoin;
import com.ruoyi.platform.service.ITaskDailyLookDramaToCoinService;

/**
 * 看剧领金币配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-22
 */
@Service
public class TaskDailyLookDramaToCoinServiceImpl implements ITaskDailyLookDramaToCoinService 
{
    @Autowired
    private TaskDailyLookDramaToCoinMapper taskDailyLookDramaToCoinMapper;

    /**
     * 查询看剧领金币配置
     * 
     * @param id 看剧领金币配置主键
     * @return 看剧领金币配置
     */
    @Override
    public TaskDailyLookDramaToCoin selectTaskDailyLookDramaToCoinById(Long id)
    {
        return taskDailyLookDramaToCoinMapper.selectTaskDailyLookDramaToCoinById(id);
    }

    /**
     * 查询看剧领金币配置列表
     * 
     * @param taskDailyLookDramaToCoin 看剧领金币配置
     * @return 看剧领金币配置
     */
    @Override
    public List<TaskDailyLookDramaToCoin> selectTaskDailyLookDramaToCoinList(TaskDailyLookDramaToCoin taskDailyLookDramaToCoin)
    {
        return taskDailyLookDramaToCoinMapper.selectTaskDailyLookDramaToCoinList(taskDailyLookDramaToCoin);
    }

    /**
     * 新增看剧领金币配置
     * 
     * @param taskDailyLookDramaToCoin 看剧领金币配置
     * @return 结果
     */
    @Override
    public int insertTaskDailyLookDramaToCoin(TaskDailyLookDramaToCoin taskDailyLookDramaToCoin)
    {
        taskDailyLookDramaToCoin.setCreateTime(DateUtils.getNowDate());
        return taskDailyLookDramaToCoinMapper.insertTaskDailyLookDramaToCoin(taskDailyLookDramaToCoin);
    }

    /**
     * 修改看剧领金币配置
     * 
     * @param taskDailyLookDramaToCoin 看剧领金币配置
     * @return 结果
     */
    @Override
    public int updateTaskDailyLookDramaToCoin(TaskDailyLookDramaToCoin taskDailyLookDramaToCoin)
    {
        taskDailyLookDramaToCoin.setUpdateTime(DateUtils.getNowDate());
        return taskDailyLookDramaToCoinMapper.updateTaskDailyLookDramaToCoin(taskDailyLookDramaToCoin);
    }

    /**
     * 批量删除看剧领金币配置
     * 
     * @param ids 需要删除的看剧领金币配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailyLookDramaToCoinByIds(Long[] ids)
    {
        return taskDailyLookDramaToCoinMapper.deleteTaskDailyLookDramaToCoinByIds(ids);
    }

    /**
     * 删除看剧领金币配置信息
     * 
     * @param id 看剧领金币配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailyLookDramaToCoinById(Long id)
    {
        return taskDailyLookDramaToCoinMapper.deleteTaskDailyLookDramaToCoinById(id);
    }
}
