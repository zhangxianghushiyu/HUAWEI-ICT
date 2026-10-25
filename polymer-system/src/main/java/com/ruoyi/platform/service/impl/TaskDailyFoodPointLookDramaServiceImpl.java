package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaskDailyFoodPointLookDramaMapper;
import com.ruoyi.platform.domain.TaskDailyFoodPointLookDrama;
import com.ruoyi.platform.service.ITaskDailyFoodPointLookDramaService;

/**
 * 饭点看剧领金币配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-22
 */
@Service
public class TaskDailyFoodPointLookDramaServiceImpl implements ITaskDailyFoodPointLookDramaService 
{
    @Autowired
    private TaskDailyFoodPointLookDramaMapper taskDailyFoodPointLookDramaMapper;

    /**
     * 查询饭点看剧领金币配置
     * 
     * @param id 饭点看剧领金币配置主键
     * @return 饭点看剧领金币配置
     */
    @Override
    public TaskDailyFoodPointLookDrama selectTaskDailyFoodPointLookDramaById(Long id)
    {
        return taskDailyFoodPointLookDramaMapper.selectTaskDailyFoodPointLookDramaById(id);
    }

    /**
     * 查询饭点看剧领金币配置列表
     * 
     * @param taskDailyFoodPointLookDrama 饭点看剧领金币配置
     * @return 饭点看剧领金币配置
     */
    @Override
    public List<TaskDailyFoodPointLookDrama> selectTaskDailyFoodPointLookDramaList(TaskDailyFoodPointLookDrama taskDailyFoodPointLookDrama)
    {
        return taskDailyFoodPointLookDramaMapper.selectTaskDailyFoodPointLookDramaList(taskDailyFoodPointLookDrama);
    }

    /**
     * 新增饭点看剧领金币配置
     * 
     * @param taskDailyFoodPointLookDrama 饭点看剧领金币配置
     * @return 结果
     */
    @Override
    public int insertTaskDailyFoodPointLookDrama(TaskDailyFoodPointLookDrama taskDailyFoodPointLookDrama)
    {
        taskDailyFoodPointLookDrama.setCreateTime(DateUtils.getNowDate());
        return taskDailyFoodPointLookDramaMapper.insertTaskDailyFoodPointLookDrama(taskDailyFoodPointLookDrama);
    }

    /**
     * 修改饭点看剧领金币配置
     * 
     * @param taskDailyFoodPointLookDrama 饭点看剧领金币配置
     * @return 结果
     */
    @Override
    public int updateTaskDailyFoodPointLookDrama(TaskDailyFoodPointLookDrama taskDailyFoodPointLookDrama)
    {
        taskDailyFoodPointLookDrama.setUpdateTime(DateUtils.getNowDate());
        return taskDailyFoodPointLookDramaMapper.updateTaskDailyFoodPointLookDrama(taskDailyFoodPointLookDrama);
    }

    /**
     * 批量删除饭点看剧领金币配置
     * 
     * @param ids 需要删除的饭点看剧领金币配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailyFoodPointLookDramaByIds(Long[] ids)
    {
        return taskDailyFoodPointLookDramaMapper.deleteTaskDailyFoodPointLookDramaByIds(ids);
    }

    /**
     * 删除饭点看剧领金币配置信息
     * 
     * @param id 饭点看剧领金币配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailyFoodPointLookDramaById(Long id)
    {
        return taskDailyFoodPointLookDramaMapper.deleteTaskDailyFoodPointLookDramaById(id);
    }
}
