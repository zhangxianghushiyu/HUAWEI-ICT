package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.TaskDailyLookVideoToCoin;

/**
 * 看视频领金币配置Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-22
 */
public interface TaskDailyLookVideoToCoinMapper 
{
    /**
     * 查询看视频领金币配置
     * 
     * @param id 看视频领金币配置主键
     * @return 看视频领金币配置
     */
    public TaskDailyLookVideoToCoin selectTaskDailyLookVideoToCoinById(Long id);

    /**
     * 查询看视频领金币配置列表
     * 
     * @param taskDailyLookVideoToCoin 看视频领金币配置
     * @return 看视频领金币配置集合
     */
    public List<TaskDailyLookVideoToCoin> selectTaskDailyLookVideoToCoinList(TaskDailyLookVideoToCoin taskDailyLookVideoToCoin);

    /**
     * 新增看视频领金币配置
     * 
     * @param taskDailyLookVideoToCoin 看视频领金币配置
     * @return 结果
     */
    public int insertTaskDailyLookVideoToCoin(TaskDailyLookVideoToCoin taskDailyLookVideoToCoin);

    /**
     * 修改看视频领金币配置
     * 
     * @param taskDailyLookVideoToCoin 看视频领金币配置
     * @return 结果
     */
    public int updateTaskDailyLookVideoToCoin(TaskDailyLookVideoToCoin taskDailyLookVideoToCoin);

    /**
     * 删除看视频领金币配置
     * 
     * @param id 看视频领金币配置主键
     * @return 结果
     */
    public int deleteTaskDailyLookVideoToCoinById(Long id);

    /**
     * 批量删除看视频领金币配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaskDailyLookVideoToCoinByIds(Long[] ids);
}
