package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.vo.TaskCompleteRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaskCompleteRecordMapper;
import com.ruoyi.platform.domain.TaskCompleteRecord;
import com.ruoyi.platform.service.ITaskCompleteRecordService;

/**
 * 任务完成记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Service
public class TaskCompleteRecordServiceImpl implements ITaskCompleteRecordService
{
    @Autowired
    private TaskCompleteRecordMapper taskCompleteRecordMapper;

    /**
     * 查询任务完成记录
     *
     * @param id 任务完成记录主键
     * @return 任务完成记录
     */
    @Override
    public TaskCompleteRecord selectTaskCompleteRecordById(Long id)
    {
        return taskCompleteRecordMapper.selectTaskCompleteRecordById(id);
    }

    /**
     * 查询任务完成记录列表
     *
     * @param taskCompleteRecord 任务完成记录
     * @return 任务完成记录
     */
    @Override
    public List<TaskCompleteRecord> selectTaskCompleteRecordList(TaskCompleteRecord taskCompleteRecord)
    {
        return taskCompleteRecordMapper.selectTaskCompleteRecordList(taskCompleteRecord);
    }

    /**
     * 查询任务完成记录列表
     */
    public List<TaskCompleteRecordVO> selectTaskCompleteRecordDetailList(TaskCompleteRecordVO taskCompleteRecordVO){
        return taskCompleteRecordMapper.selectTaskCompleteRecordDetailList(taskCompleteRecordVO);
    }


    /**
     * 新增任务完成记录
     *
     * @param taskCompleteRecord 任务完成记录
     * @return 结果
     */
    @Override
    public int insertTaskCompleteRecord(TaskCompleteRecord taskCompleteRecord)
    {
        taskCompleteRecord.setCreateTime(DateUtils.getNowDate());
        return taskCompleteRecordMapper.insertTaskCompleteRecord(taskCompleteRecord);
    }

    /**
     * 修改任务完成记录
     *
     * @param taskCompleteRecord 任务完成记录
     * @return 结果
     */
    @Override
    public int updateTaskCompleteRecord(TaskCompleteRecord taskCompleteRecord)
    {
        taskCompleteRecord.setUpdateTime(DateUtils.getNowDate());
        return taskCompleteRecordMapper.updateTaskCompleteRecord(taskCompleteRecord);
    }

    /**
     * 批量删除任务完成记录
     *
     * @param ids 需要删除的任务完成记录主键
     * @return 结果
     */
    @Override
    public int deleteTaskCompleteRecordByIds(Long[] ids)
    {
        return taskCompleteRecordMapper.deleteTaskCompleteRecordByIds(ids);
    }

    /**
     * 删除任务完成记录信息
     *
     * @param id 任务完成记录主键
     * @return 结果
     */
    @Override
    public int deleteTaskCompleteRecordById(Long id)
    {
        return taskCompleteRecordMapper.deleteTaskCompleteRecordById(id);
    }
}
