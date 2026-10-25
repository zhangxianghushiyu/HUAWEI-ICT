package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.TaskDailyAppointDramaCoinInfo;

/**
 * 看指定剧领金币配置信息Service接口
 * 
 * @author ruoyi
 * @date 2024-11-25
 */
public interface ITaskDailyAppointDramaCoinInfoService 
{
    /**
     * 查询看指定剧领金币配置信息
     * 
     * @param id 看指定剧领金币配置信息主键
     * @return 看指定剧领金币配置信息
     */
    public TaskDailyAppointDramaCoinInfo selectTaskDailyAppointDramaCoinInfoById(Long id);

    /**
     * 查询看指定剧领金币配置信息列表
     * 
     * @param taskDailyAppointDramaCoinInfo 看指定剧领金币配置信息
     * @return 看指定剧领金币配置信息集合
     */
    public List<TaskDailyAppointDramaCoinInfo> selectTaskDailyAppointDramaCoinInfoList(TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo);

    /**
     * 新增看指定剧领金币配置信息
     * 
     * @param taskDailyAppointDramaCoinInfo 看指定剧领金币配置信息
     * @return 结果
     */
    public int insertTaskDailyAppointDramaCoinInfo(TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo);

    /**
     * 修改看指定剧领金币配置信息
     * 
     * @param taskDailyAppointDramaCoinInfo 看指定剧领金币配置信息
     * @return 结果
     */
    public int updateTaskDailyAppointDramaCoinInfo(TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo);

    /**
     * 批量删除看指定剧领金币配置信息
     * 
     * @param ids 需要删除的看指定剧领金币配置信息主键集合
     * @return 结果
     */
    public int deleteTaskDailyAppointDramaCoinInfoByIds(Long[] ids);

    /**
     * 删除看指定剧领金币配置信息信息
     * 
     * @param id 看指定剧领金币配置信息主键
     * @return 结果
     */
    public int deleteTaskDailyAppointDramaCoinInfoById(Long id);
}
