package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.TaskReward;

/**
 * 任务Service接口
 * 
 * @author ruoyi
 * @date 2024-11-19
 */
public interface ITaskRewardService 
{
    /**
     * 查询任务
     * 
     * @param id 任务主键
     * @return 任务
     */
    public TaskReward selectTaskRewardById(Long id);

    /**
     * 查询任务列表
     * 
     * @param taskReward 任务
     * @return 任务集合
     */
    public List<TaskReward> selectTaskRewardList(TaskReward taskReward);

    /**
     * 新增任务
     * 
     * @param taskReward 任务
     * @return 结果
     */
    public int insertTaskReward(TaskReward taskReward);

    /**
     * 修改任务
     * 
     * @param taskReward 任务
     * @return 结果
     */
    public int updateTaskReward(TaskReward taskReward);

    /**
     * 批量删除任务
     * 
     * @param ids 需要删除的任务主键集合
     * @return 结果
     */
    public int deleteTaskRewardByIds(Long[] ids);

    /**
     * 删除任务信息
     * 
     * @param id 任务主键
     * @return 结果
     */
    public int deleteTaskRewardById(Long id);
}
