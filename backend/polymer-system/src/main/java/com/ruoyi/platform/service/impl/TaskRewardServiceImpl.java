package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaskRewardMapper;
import com.ruoyi.platform.domain.TaskReward;
import com.ruoyi.platform.service.ITaskRewardService;

/**
 * 任务Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-19
 */
@Service
public class TaskRewardServiceImpl implements ITaskRewardService 
{
    @Autowired
    private TaskRewardMapper taskRewardMapper;

    /**
     * 查询任务
     * 
     * @param id 任务主键
     * @return 任务
     */
    @Override
    public TaskReward selectTaskRewardById(Long id)
    {
        return taskRewardMapper.selectTaskRewardById(id);
    }

    /**
     * 查询任务列表
     * 
     * @param taskReward 任务
     * @return 任务
     */
    @Override
    public List<TaskReward> selectTaskRewardList(TaskReward taskReward)
    {
        return taskRewardMapper.selectTaskRewardList(taskReward);
    }

    /**
     * 新增任务
     * 
     * @param taskReward 任务
     * @return 结果
     */
    @Override
    public int insertTaskReward(TaskReward taskReward)
    {
        taskReward.setCreateTime(DateUtils.getNowDate());
        return taskRewardMapper.insertTaskReward(taskReward);
    }

    /**
     * 修改任务
     * 
     * @param taskReward 任务
     * @return 结果
     */
    @Override
    public int updateTaskReward(TaskReward taskReward)
    {
        taskReward.setUpdateTime(DateUtils.getNowDate());
        return taskRewardMapper.updateTaskReward(taskReward);
    }

    /**
     * 批量删除任务
     * 
     * @param ids 需要删除的任务主键
     * @return 结果
     */
    @Override
    public int deleteTaskRewardByIds(Long[] ids)
    {
        return taskRewardMapper.deleteTaskRewardByIds(ids);
    }

    /**
     * 删除任务信息
     * 
     * @param id 任务主键
     * @return 结果
     */
    @Override
    public int deleteTaskRewardById(Long id)
    {
        return taskRewardMapper.deleteTaskRewardById(id);
    }
}
