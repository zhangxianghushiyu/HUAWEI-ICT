package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.TaskDailyLookDramaToCoin;

/**
 * 看剧领金币配置Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-22
 */
public interface TaskDailyLookDramaToCoinMapper 
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
     * 删除看剧领金币配置
     * 
     * @param id 看剧领金币配置主键
     * @return 结果
     */
    public int deleteTaskDailyLookDramaToCoinById(Long id);

    /**
     * 批量删除看剧领金币配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaskDailyLookDramaToCoinByIds(Long[] ids);
}
