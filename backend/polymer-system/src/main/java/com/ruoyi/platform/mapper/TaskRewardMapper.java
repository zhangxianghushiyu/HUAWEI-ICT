package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.TaskReward;

/**
 * 任务Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-19
 */
public interface TaskRewardMapper 
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
     * 删除任务
     * 
     * @param id 任务主键
     * @return 结果
     */
    public int deleteTaskRewardById(Long id);

    /**
     * 批量删除任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaskRewardByIds(Long[] ids);
}
