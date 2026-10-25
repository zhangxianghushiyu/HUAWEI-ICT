package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.dto.TaskLinkDramaListDTO;
import com.ruoyi.platform.vo.TaskLinkDramaListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaskDailyAppointDramaCoinMapper;
import com.ruoyi.platform.domain.TaskDailyAppointDramaCoin;
import com.ruoyi.platform.service.ITaskDailyAppointDramaCoinService;

/**
 * 看指定剧领金币配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-25
 */
@Service
public class TaskDailyAppointDramaCoinServiceImpl implements ITaskDailyAppointDramaCoinService
{
    @Autowired
    private TaskDailyAppointDramaCoinMapper taskDailyAppointDramaCoinMapper;

    /**
     * 查询看指定剧领金币配置
     *
     * @param id 看指定剧领金币配置主键
     * @return 看指定剧领金币配置
     */
    @Override
    public TaskDailyAppointDramaCoin selectTaskDailyAppointDramaCoinById(Long id)
    {
        return taskDailyAppointDramaCoinMapper.selectTaskDailyAppointDramaCoinById(id);
    }

    /**
     * 查询看指定剧领金币配置列表
     * @param taskLinkDramaListVO 看指定剧领金币配置
//     * @param taskDailyAppointDramaCoin 看指定剧领金币配置
     * @return 看指定剧领金币配置
     */
//    @Override
//    public List<TaskDailyAppointDramaCoin> selectTaskDailyAppointDramaCoinList(TaskDailyAppointDramaCoin taskDailyAppointDramaCoin)
//    {
//        return taskDailyAppointDramaCoinMapper.selectTaskDailyAppointDramaCoinList(taskDailyAppointDramaCoin);
//    }
    @Override
    public List<TaskLinkDramaListVO> selectTaskLinkDramaCoinList(TaskLinkDramaListDTO taskLinkDramaListDTO){
        return taskDailyAppointDramaCoinMapper.selectTaskLinkDramaCoinList(taskLinkDramaListDTO);
    }
    /**
     * 新增看指定剧领金币配置
     *
     * @param taskDailyAppointDramaCoin 看指定剧领金币配置
     * @return 结果
     */
    @Override
    public int insertTaskDailyAppointDramaCoin(TaskDailyAppointDramaCoin taskDailyAppointDramaCoin)
    {
        taskDailyAppointDramaCoin.setCreateTime(DateUtils.getNowDate());
        return taskDailyAppointDramaCoinMapper.insertTaskDailyAppointDramaCoin(taskDailyAppointDramaCoin);
    }

    /**
     * 修改看指定剧领金币配置
     *
     * @param taskDailyAppointDramaCoin 看指定剧领金币配置
     * @return 结果
     */
    @Override
    public int updateTaskDailyAppointDramaCoin(TaskDailyAppointDramaCoin taskDailyAppointDramaCoin)
    {
        taskDailyAppointDramaCoin.setUpdateTime(DateUtils.getNowDate());
        return taskDailyAppointDramaCoinMapper.updateTaskDailyAppointDramaCoin(taskDailyAppointDramaCoin);
    }

    /**
     * 批量删除看指定剧领金币配置
     *
     * @param ids 需要删除的看指定剧领金币配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailyAppointDramaCoinByIds(Long[] ids)
    {
        return taskDailyAppointDramaCoinMapper.deleteTaskDailyAppointDramaCoinByIds(ids);
    }

    /**
     * 删除看指定剧领金币配置信息
     *
     * @param id 看指定剧领金币配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskDailyAppointDramaCoinById(Long id)
    {
        return taskDailyAppointDramaCoinMapper.deleteTaskDailyAppointDramaCoinById(id);
    }

    /**
     * 查询短剧
     */
    @Override
    public int selectTaskLinkDramaIdList(TaskLinkDramaListVO taskLinkDramaListVO){
        return taskDailyAppointDramaCoinMapper.selectTaskLinkDramaIdList (taskLinkDramaListVO);
    }
}
