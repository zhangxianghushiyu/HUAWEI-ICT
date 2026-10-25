package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.TaskDailyLookDramaToCoin;

/**
 * 看剧领金币配置Service接口
 * 
 * @author ruoyi
 * @date 2024-11-22
 */
public interface ITaskDailyLookDramaToCoinService 
{
    /**
     * 查询看剧领金币配置
     * 
     * @param id 看剧领金币配置主键
     * @return 看剧领金币配置
     */
    public TaskDailyLookDramaToCoin selectTaskDailyLookDramaToCoinById(Long id);

    /**
     * 查询看剧领金币配置列表
     * 
     * @param taskDailyLookDramaToCoin 看剧领金币配置
     * @return 看剧领金币配置集合
     */
    public List<TaskDailyLookDramaToCoin> selectTaskDailyLookDramaToCoinList(TaskDailyLookDramaToCoin taskDailyLookDramaToCoin);

    /**
     * 新增看剧领金币配置
     * 
     * @param taskDailyLookDramaToCoin 看剧领金币配置
     * @return 结果
     */
    public int insertTaskDailyLookDramaToCoin(TaskDailyLookDramaToCoin taskDailyLookDramaToCoin);

    /**
     * 修改看剧领金币配置
     * 
     * @param taskDailyLookDramaToCoin 看剧领金币配置
     * @return 结果
     */
    public int updateTaskDailyLookDramaToCoin(TaskDailyLookDramaToCoin taskDailyLookDramaToCoin);

    /**
     * 批量删除看剧领金币配置
     * 
     * @param ids 需要删除的看剧领金币配置主键集合
     * @return 结果
     */
    public int deleteTaskDailyLookDramaToCoinByIds(Long[] ids);

    /**
     * 删除看剧领金币配置信息
     * 
     * @param id 看剧领金币配置主键
     * @return 结果
     */
    public int deleteTaskDailyLookDramaToCoinById(Long id);
}
