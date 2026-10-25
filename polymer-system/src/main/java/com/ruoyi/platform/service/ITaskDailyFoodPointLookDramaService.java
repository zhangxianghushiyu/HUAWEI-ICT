package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.TaskDailyFoodPointLookDrama;

/**
 * 饭点看剧领金币配置Service接口
 * 
 * @author ruoyi
 * @date 2024-11-22
 */
public interface ITaskDailyFoodPointLookDramaService 
{
    /**
     * 查询饭点看剧领金币配置
     * 
     * @param id 饭点看剧领金币配置主键
     * @return 饭点看剧领金币配置
     */
    public TaskDailyFoodPointLookDrama selectTaskDailyFoodPointLookDramaById(Long id);

    /**
     * 查询饭点看剧领金币配置列表
     * 
     * @param taskDailyFoodPointLookDrama 饭点看剧领金币配置
     * @return 饭点看剧领金币配置集合
     */
    public List<TaskDailyFoodPointLookDrama> selectTaskDailyFoodPointLookDramaList(TaskDailyFoodPointLookDrama taskDailyFoodPointLookDrama);

    /**
     * 新增饭点看剧领金币配置
     * 
     * @param taskDailyFoodPointLookDrama 饭点看剧领金币配置
     * @return 结果
     */
    public int insertTaskDailyFoodPointLookDrama(TaskDailyFoodPointLookDrama taskDailyFoodPointLookDrama);

    /**
     * 修改饭点看剧领金币配置
     * 
     * @param taskDailyFoodPointLookDrama 饭点看剧领金币配置
     * @return 结果
     */
    public int updateTaskDailyFoodPointLookDrama(TaskDailyFoodPointLookDrama taskDailyFoodPointLookDrama);

    /**
     * 批量删除饭点看剧领金币配置
     * 
     * @param ids 需要删除的饭点看剧领金币配置主键集合
     * @return 结果
     */
    public int deleteTaskDailyFoodPointLookDramaByIds(Long[] ids);

    /**
     * 删除饭点看剧领金币配置信息
     * 
     * @param id 饭点看剧领金币配置主键
     * @return 结果
     */
    public int deleteTaskDailyFoodPointLookDramaById(Long id);
}
