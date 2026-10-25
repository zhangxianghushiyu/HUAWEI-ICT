package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaskDailySignMapper;
import com.ruoyi.platform.domain.TaskDailySign;
import com.ruoyi.platform.service.ITaskDailySignService;

/**
 * 每日七日签到配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class TaskDailySignServiceImpl implements ITaskDailySignService 
{
    @Autowired
    private TaskDailySignMapper taskDailySignMapper;

    /**
     * 查询每日七日签到配置
     * 
     * @param id 每日七日签到配置主键
     * @return 每日七日签到配置
     */
    @Override
    public TaskDailySign selectTaskDailySignById(Long id)
    {
        return taskDailySignMapper.selectTaskDailySignById(id);
    }

    /**
     * 查询每日七日签到配置列表
     * 
     * @param taskDailySign 每日七日签到配置
     * @return 每日七日签到配置
     */
    @Override
    public List<TaskDailySign> selectTaskDailySignList(TaskDailySign taskDailySign)
    {
        return taskDailySignMapper.selectTaskDailySignList(taskDailySign);
    }

    /**
     * 新增每日七日签到配置
     * 
     * @param taskDailySign 每日七日签到配置
     * @return 结果
     */
    @Override
    public int insertTaskDailySign(TaskDailySign taskDailySign)
    {
        taskDailySign.setCreateTime(DateUtils.getNowDate());
        return taskDailySignMapper.insertTaskDailySign(taskDailySign);
    }

    /**
     * 修改每日七日签到配置
     * 
     * @param taskDailySign 每日七日签到配置
     * @return 结果
     */
    @Override
    public int updateTaskDailySign(TaskDailySign taskDailySign)
    {
        taskDailySign.setUpdateTime(DateUtils.getNowDate());
        return taskDailySignMapper.updateTaskDailySign(taskDailySign);
    }

    /**
     * 批量删除每日七日签到配置
     * 
     * @param ids 需要删除的每日七日签到配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailySignByIds(Long[] ids)
    {
        return taskDailySignMapper.deleteTaskDailySignByIds(ids);
    }

    /**
     * 删除每日七日签到配置信息
     * 
     * @param id 每日七日签到配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailySignById(Long id)
    {
        return taskDailySignMapper.deleteTaskDailySignById(id);
    }
}
