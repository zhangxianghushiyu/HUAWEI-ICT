package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.TaskDailySign;

/**
 * 每日七日签到配置Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-21
 */
public interface TaskDailySignMapper 
{
    /**
     * 查询每日七日签到配置
     * 
     * @param id 每日七日签到配置主键
     * @return 每日七日签到配置
     */
    public TaskDailySign selectTaskDailySignById(Long id);

    /**
     * 查询每日七日签到配置列表
     * 
     * @param taskDailySign 每日七日签到配置
     * @return 每日七日签到配置集合
     */
    public List<TaskDailySign> selectTaskDailySignList(TaskDailySign taskDailySign);

    /**
     * 新增每日七日签到配置
     * 
     * @param taskDailySign 每日七日签到配置
     * @return 结果
     */
    public int insertTaskDailySign(TaskDailySign taskDailySign);

    /**
     * 修改每日七日签到配置
     * 
     * @param taskDailySign 每日七日签到配置
     * @return 结果
     */
    public int updateTaskDailySign(TaskDailySign taskDailySign);

    /**
     * 删除每日七日签到配置
     * 
     * @param id 每日七日签到配置主键
     * @return 结果
     */
    public int deleteTaskDailySignById(Long id);

    /**
     * 批量删除每日七日签到配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaskDailySignByIds(Long[] ids);
}
