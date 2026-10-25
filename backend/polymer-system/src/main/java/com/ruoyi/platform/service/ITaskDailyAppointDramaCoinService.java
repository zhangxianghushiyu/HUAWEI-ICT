package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.TaskDailyAppointDramaCoin;
import com.ruoyi.platform.dto.TaskLinkDramaListDTO;
import com.ruoyi.platform.vo.TaskLinkDramaListVO;

/**
 * 看指定剧领金币配置Service接口
 *
 * @author ruoyi
 * @date 2024-11-25
 */
public interface ITaskDailyAppointDramaCoinService
{
    /**
     * 查询看指定剧领金币配置
     *
     * @param id 看指定剧领金币配置主键
     * @return 看指定剧领金币配置
     */
    public TaskDailyAppointDramaCoin selectTaskDailyAppointDramaCoinById(Long id);

    /**
     * 查询看指定剧领金币配置列表
     * @param taskLinkDramaListVO 看指定剧领金币配置
//     * @param taskDailyAppointDramaCoin 看指定剧领金币配置
     * @return 看指定剧领金币配置集合
     */
//    public List<TaskDailyAppointDramaCoin> selectTaskDailyAppointDramaCoinList(TaskDailyAppointDramaCoin taskDailyAppointDramaCoin);\
    public List<TaskLinkDramaListVO> selectTaskLinkDramaCoinList(TaskLinkDramaListDTO taskLinkDramaListDTO);
    /**
     * 新增看指定剧领金币配置
     *
     * @param taskDailyAppointDramaCoin 看指定剧领金币配置
     * @return 结果
     */
    public int insertTaskDailyAppointDramaCoin(TaskDailyAppointDramaCoin taskDailyAppointDramaCoin);

    /**
     * 修改看指定剧领金币配置
     *
     * @param taskDailyAppointDramaCoin 看指定剧领金币配置
     * @return 结果
     */
    public int updateTaskDailyAppointDramaCoin(TaskDailyAppointDramaCoin taskDailyAppointDramaCoin);

    /**
     * 批量删除看指定剧领金币配置
     *
     * @param ids 需要删除的看指定剧领金币配置主键集合
     * @return 结果
     */
    public int deleteTaskDailyAppointDramaCoinByIds(Long[] ids);

    /**
     * 删除看指定剧领金币配置信息
     *
     * @param id 看指定剧领金币配置主键
     * @return 结果
     */
    public int deleteTaskDailyAppointDramaCoinById(Long id);

    /**
     * 查询短剧详情
     */
    public int selectTaskLinkDramaIdList(TaskLinkDramaListVO taskLinkDramaListVO);
}
