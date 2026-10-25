package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.TaskCompleteRecord;
import com.ruoyi.platform.vo.TaskCompleteRecordVO;

/**
 * 任务完成记录Service接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface ITaskCompleteRecordService
{
    /**
     * 查询任务完成记录
     *
     * @param id 任务完成记录主键
     * @return 任务完成记录
     */
    public TaskCompleteRecord selectTaskCompleteRecordById(Long id);

    /**
     * 查询任务完成记录列表
     *
     * @param taskCompleteRecord 任务完成记录
     * @return 任务完成记录集合
     */
    public List<TaskCompleteRecord> selectTaskCompleteRecordList(TaskCompleteRecord taskCompleteRecord);

    /**
     * 查询任务完成记录列表
     *
     * @param taskCompleteRecordVO 任务完成记录
     * @return 任务完成记录集合
     */
    public List<TaskCompleteRecordVO> selectTaskCompleteRecordDetailList(TaskCompleteRecordVO taskCompleteRecordVO);


    /**
     * 新增任务完成记录
     *
     * @param taskCompleteRecord 任务完成记录
     * @return 结果
     */
    public int insertTaskCompleteRecord(TaskCompleteRecord taskCompleteRecord);

    /**
     * 修改任务完成记录
     *
     * @param taskCompleteRecord 任务完成记录
     * @return 结果
     */
    public int updateTaskCompleteRecord(TaskCompleteRecord taskCompleteRecord);

    /**
     * 批量删除任务完成记录
     *
     * @param ids 需要删除的任务完成记录主键集合
     * @return 结果
     */
    public int deleteTaskCompleteRecordByIds(Long[] ids);

    /**
     * 删除任务完成记录信息
     *
     * @param id 任务完成记录主键
     * @return 结果
     */
    public int deleteTaskCompleteRecordById(Long id);
}
